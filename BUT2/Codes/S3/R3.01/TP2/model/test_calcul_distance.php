<?php
    require_once 'CalculDistanceImpl.php';

    class TestCalculDistance {

        public static function main() {
            $filePath = 'test.json';
            
            $fileContent = file_get_contents($filePath);
            if (!$fileContent) {
                die("Error reading file $filePath");
            }

            $parsedData = json_decode($fileContent, true);
            if (json_last_error() !== JSON_ERROR_NONE) {
                die("Error decoding JSON: " . json_last_error_msg());
            }

            $parcours = [];
            foreach ($parsedData['data'] as $entry) {
                $parcours[] = [
                    'lat' => $entry['latitude'],
                    'long' => $entry['longitude']
                ];
            }

            $calculDistance = new CalculDistanceImpl();
            $distance = $calculDistance -> calculDistanceTrajet($parcours);

            echo "The total distance of the journey is: " . $distance . " meters.\n";
        }
    }

    TestCalculDistance::main();
?>
