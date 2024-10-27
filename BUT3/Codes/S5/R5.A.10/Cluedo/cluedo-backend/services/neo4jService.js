const neo4j = require("neo4j-driver")

const driver = neo4j.driver("bolt://localhost:7687", neo4j.auth.basic("neo4j", "azertyuiop"))

const getSession = () => {
  return driver.session({ database: "cluedo" })
}

const closeSession = (session) => {
  session.close()
}

module.exports = {
  getSession,
  closeSession,
}
