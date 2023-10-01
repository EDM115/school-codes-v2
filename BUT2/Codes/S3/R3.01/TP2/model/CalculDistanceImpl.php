<?php
    require_once 'CalculDistance.php';

    class CalculDistanceImpl implements CalculDistance {

        const EARTH_RADIUS = 6371000; // rayon de la Terre en mètres

        /**
         * Retourne la distance en mètres entre 2 points GPS exprimés en degrés.
         * @param float $lat1 Latitude du premier point GPS
         * @param float $long1 Longitude du premier point GPS
         * @param float $lat2 Latitude du second point GPS
         * @param float $long2 Longitude du second point GPS
         * @return float La distance entre les deux points GPS
         */
        public function calculDistance2PointsGPS(float $lat1, float $long1, float $lat2, float $long2): float {
            $lat1 = deg2rad($lat1);
            $long1 = deg2rad($long1);
            $lat2 = deg2rad($lat2);
            $long2 = deg2rad($long2);

            $deltaLat = $lat2 - $lat1;
            $deltaLong = $long2 - $long1;

            $a = sin($deltaLat / 2) * sin($deltaLat / 2) +
                cos($lat1) * cos($lat2) * 
                sin($deltaLong / 2) * sin($deltaLong / 2);
            
            $c = 2 * atan2(sqrt($a), sqrt(1 - $a));

            return self::EARTH_RADIUS * $c;
        }

        /**
         * Retourne la distance en mètres du parcours passé en paramètres. Le parcours est
         * défini par un tableau ordonné de points GPS.
         * @param Array $parcours Le tableau contenant les points GPS
         * @return float La distance du parcours
         */
        public function calculDistanceTrajet(Array $parcours): float {
            $distanceTotale = 0;

            for ($i = 0; $i < count($parcours) - 1; $i++) {
                $pointActuel = $parcours[$i];
                $pointSuivant = $parcours[$i + 1];

                $distanceTotale += $this -> calculDistance2PointsGPS(
                    $pointActuel['lat'], $pointActuel['long'],
                    $pointSuivant['lat'], $pointSuivant['long']
                );
            }

            return $distanceTotale;
        }
    }
?>
