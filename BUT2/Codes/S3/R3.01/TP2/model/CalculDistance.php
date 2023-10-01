<?php
    interface CalculDistance {
        /**
         * Retourne la distance en mètres entre 2 points GPS exprimés en degrés.
         * @param float $lat1 Latitude du premier point GPS
         * @param float $long1 Longitude du premier point GPS
         * @param float $lat2 Latitude du second point GPS
         * @param float $long2 Longitude du second point GPS
         * @return float La distance entre les deux points GPS
         */
        public function calculDistance2PointsGPS(float $lat1, float $long1, float $lat2, float $long2): float;

        /**
         * Retourne la distance en mètres du parcours passé en paramètres. Le parcours est
         * défini par un tableau ordonné de points GPS.
         * @param Array $parcours Le tableau contenant les points GPS
         * @return float La distance du parcours
         */
        public function calculDistanceTrajet(Array $parcours): float;
    }
?>
