const { MongoClient } = require('mongodb')

const url = 'mongodb://localhost:27017'
const client = new MongoClient(url)

async function run() {
    try {
        await client.connect()
        console.log("Connection réussie à MongoDB")

        console.log("\n\t1 - use Cinema")
        const db = client.db('Cinema')
        console.log(db.s.namespace)

        console.log("\n\t2 - db.createCollection('Films')")
        const create = await db.createCollection("Films")
        console.log(create.s.namespace)

        console.log("\n\t3 - Insertion de plusieurs films")
        const insertResult = await db.collection('Films').insertMany([
            {
                title: "Interstellar",
                director: "Christopher Nolan",
                poster_url: "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
                actors: ["Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"],
                showtimes: [
                    { date: "2024-04-20", time: "14:00" },
                    { date: "2024-04-20", time: "18:00" }
                ]
            },
            {
                title: "Matrix",
                director: "Lana Wachowski, Lilly Wachowski",
                poster_url: "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg",
                actors: ["Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"],
                showtimes: [
                    { date: "2024-04-28", time: "17:00" },
                    { date: "2024-04-28", time: "21:00" }
                ]
            },
            {
                title: "Tenet",
                director: "Christopher Nolan",
                poster_url: "https://m.media-amazon.com/images/M/MV5BMzU3YWYwNTQtZTdiMC00NjY5LTlmMTMtZDFlYTEyODBjMTk5XkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg",
                actors: ["John David Washington", "Robert Pattinson", "Elizabeth Debicki"],
                showtimes: [
                    { date: "2024-04-29", time: "14:00" },
                    { date: "2024-04-29", time: "18:00" }
                ]
            },
            {
                title: "Cars",
                director: "John Lasseter",
                poster_url: "https://m.media-amazon.com/images/M/MV5BMTg5NzY0MzA2MV5BMl5BanBnXkFtZTYwNDc3NTc2._V1_.jpg",
                actors: ["Owen Wilson", "Paul Newman", "Bonnie Hunt"],
                showtimes: [
                    { date: "2024-04-30", time: "16:00" },
                    { date: "2024-04-30", time: "20:00" }
                ]
            },
            {
                title: "Inception",
                director: "Christopher Nolan",
                poster_url: "https://m.media-amazon.com/images/I/912AErFSBHL._AC_UF1000,1000_QL80_.jpg",
                actors: ["Leonardo DiCaprio", "Joseph Gordon-Levitt", "Ellen Page"],
                showtimes: [
                    { date: "2024-05-01", time: "15:00" },
                    { date: "2024-05-01", time: "19:00" }
                ]
            },
            {
                title: "Divergente",
                director: "Neil Burger",
                poster_url: "https://m.media-amazon.com/images/M/MV5BMTYxMzYwODE4OV5BMl5BanBnXkFtZTgwNDE5MzE2MDE@._V1_.jpg",
                actors: ["Shailene Woodley", "Theo James", "Kate Winslet"],
                showtimes: [
                    { date: "2024-05-02", time: "14:00" },
                    { date: "2024-05-02", time: "18:00" }
                ]
            },
            {
                title: "Prometheus",
                director: "Ridley Scott",
                poster_url: "https://m.media-amazon.com/images/M/MV5BMTY3NzIyNTA2NV5BMl5BanBnXkFtZTcwNzE2NjI4Nw@@._V1_.jpg",
                actors: ["Noomi Rapace", "Michael Fassbender", "Guy Pearce"],
                showtimes: [
                    { date: "2024-05-03", time: "17:00" },
                    { date: "2024-05-03", time: "21:00" }
                ]
            },
            {
                title: "Looper",
                director: "Rian Johnson",
                poster_url: "https://m.media-amazon.com/images/M/MV5BMTg5NTA3NTg4NF5BMl5BanBnXkFtZTcwNTA0NDYzOA@@._V1_.jpg",
                actors: ["Bruce Willis", "Joseph Gordon-Levitt", "Emily Blunt"],
                showtimes: [
                    { date: "2024-05-04", time: "16:00" },
                    { date: "2024-05-04", time: "20:00" }
                ]
            }	
        ])
        console.log(insertResult)

        console.log("\n\t4 - Suppression d'un film (Inception)")
        const deleteResult = await db.collection('Films').deleteOne({ title: "Inception" })
        console.log(deleteResult)

        console.log("\n\t(Ajout à nouveau du film Inception)")
        const reinsertResult = await db.collection('Films').insertOne({
            title: "Inception",
            director: "Christopher Nolan",
            poster_url: "https://m.media-amazon.com/images/I/912AErFSBHL._AC_UF1000,1000_QL80_.jpg",
            actors: ["Leonardo DiCaprio", "Joseph Gordon-Levitt", "Ellen Page"],
            showtimes: [
                { date: "2024-05-01", time: "15:00" },
                { date: "2024-05-01", time: "19:00" }
            ]
        })
        console.log(reinsertResult)

        console.log("\n\t5 - Affichage des acteurs d'Inception")
        const actorsResult = await db.collection('Films').findOne({ title: "Inception" }, { projection: { actors: 1, _id: 0 } })
        console.log(actorsResult)

        console.log("\n\t6 - Lister les films de Christopher Nolan")
        const nolanFilms = await db.collection('Films').find({ director: "Christopher Nolan" }).toArray()
        console.log(nolanFilms)

        console.log("\n\t7 - Lister les films avec Bruce Willis")
        const willisFilms = await db.collection('Films').find({ actors: "Bruce Willis" }).toArray()
        console.log(willisFilms)

        console.log("\n\t8 - Afficher les horaires de Cars")
        const carsShowtimes = await db.collection('Films').findOne({ title: "Cars" }, { projection: { showtimes: 1, _id: 0 } })
        console.log(carsShowtimes)

        console.log("\n\t9 - Lister les films à un horaire spécifique")
        const specificTimeFilms = await db.collection('Films').find({ "showtimes": { $elemMatch: { date: "2024-04-20", time: "14:00" } } }).toArray()
        console.log(specificTimeFilms)

        console.log("\n\t10 - Changer l'affiche d'Inception")
        const updatePoster = await db.collection('Films').updateOne({ title: "Inception" }, { $set: { poster_url: "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg" } })
        console.log(updatePoster)

        console.log("\n\t11 - Ajouter de nouveaux horaires pour Divergente")
        const addShowtime = await db.collection('Films').updateOne({ title: "Divergente" }, { $push: { showtimes: { date: "2024-05-03", time: "20:00" } } })
        console.log(addShowtime)

        console.log("\n\t12 - Supprimer certains horaires de Divergente")
        const removeShowtime = await db.collection('Films').updateOne({ title: "Divergente" }, { $pull: { showtimes: { date: "2024-05-03", time: "20:00" } } })
        console.log(removeShowtime)

        console.log("\n\t13 - Ajouter des réservations à Matrix")
        const addReservations = await db.collection('Films').updateOne({ title: "Matrix", "showtimes.date": "2024-04-28", "showtimes.time": "17:00" }, { $set: { "showtimes.$.reservations": 50 } })
        console.log(addReservations)

        console.log("\n\t14 - Ajouter le nombe de places restantes pour Matrix")
        const addRemainingSeats = await db.collection('Films').updateOne({ title: "Matrix", "showtimes.date": "2024-04-28", "showtimes.time": "17:00" }, { $set: { "showtimes.$.remaining_seats": 20 } })
        console.log(addRemainingSeats)

        console.log("\n\t15 - Renseigner les films restreints aux +12 ans (Prometheus)")
        const addRestriction = await db.collection('Films').updateOne({ title: "Prometheus" }, { $set: { age_restriction: "+12" } })
        console.log(addRestriction)

    } finally {
        await client.close()
    }
}

run().catch(console.dir)
