import java.sql.Connection

import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.DBApi
import play.api.db.evolutions._

class EvolutionsSpecs extends PlaySpec with GuiceOneAppPerSuite with BeforeAndAfter {

  import EvolutionsSpecs._

  before {
    val dbApi = app.injector.instanceOf[DBApi]
    val db    = dbApi.database(DatabaseName)

    Evolutions.applyEvolutions(db)
  }

  "Evolutions" should {
    s"succeed Downs and Ups scripts if $BaseRevision.sql or later" in {
      val dbApi         = app.injector.instanceOf[DBApi]
      val evolutionsApi = app.injector.instanceOf[EvolutionsApi]

      val db = dbApi.database(DatabaseName)
      try {
        // get evolutions from base revision from play_evolutions table
        val evolutions = db.withConnection(autocommit = false)(loadEvolutions(BaseRevision))

        assert(evolutions.nonEmpty)

        // apply downs scripts from latest revision
        val downs = evolutions.reverse.map(e => DownScript(e))
        evolutionsApi.evolve(DatabaseName, downs, autocommit = false, schema = "")

        // apply ups scripts from base revision
        val ups = evolutions.map(e => UpScript(e))
        evolutionsApi.evolve(DatabaseName, ups, autocommit = false, schema = "")

        succeed
      } catch {
        case e: InconsistentDatabase =>
          fail(s"${e.subTitle()} ${e.content()}", e)
      }
    }
  }
}

object EvolutionsSpecs {

  val DatabaseName: String = "default"
  val BaseRevision: Int    = 2

  def loadEvolutions(fromRevision: Int): Connection => Seq[Evolution] = { c =>
    val statement =
      c.prepareStatement(
        """SELECT id, apply_script, revert_script
          |FROM play_evolutions
          |WHERE id >= ?
          |ORDER BY id""".stripMargin
      )
    statement.setInt(1, fromRevision)

    val resultSet = statement.executeQuery()
    Iterator
      .iterate(resultSet)(identity)
      .takeWhile(_.next())
      .map { rs =>
        Evolution(
          rs.getInt("id"),
          rs.getString("apply_script"),
          rs.getString("revert_script")
        )
      }
      .toList
  }

}
