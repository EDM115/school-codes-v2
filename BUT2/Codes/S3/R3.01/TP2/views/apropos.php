<?php
    include __ROOT__ . "/views/header.html";
?>

<div class="drac-box drac-w-xl drac-scrollbar-purple drac-rounded-lg drac-p-md">
<br>
    <h2 class="drac-heading drac-heading-lg drac-text-purple">À propos</h2>
    <br>
        <span class="drac-text drac-line-height drac-text-white">Créé par :
        <br>
            <span class="drac-text drac-text-semibold drac-line-height drac-text-green">EDM115<br>Bazo</span>
        </span>
        <br>

<?php
    $linesOfCode = 0;

    function countLines($file)
    {
        $lines = file($file);
        if ($lines === false) {
            return 0;
        }
        return count($lines);
    }

    function browse($dir)
    {
        global $linesOfCode;
        $files = glob($dir . "/*");
        foreach ($files as $file) {
            if (is_dir($file)) {
                browse($file);
            } else {
                $linesOfCode += countLines($file);
            }
        }
        return $linesOfCode;
    }

    $linesOfCode = browse(__ROOT__);

    echo "<br><span class=\"drac-text drac-line-height drac-text-white\">Lignes de code :<br><span class=\"drac-text drac-text-semibold drac-line-height drac-text-green\">" . $linesOfCode . "</span></span>";

    include __ROOT__ . "/views/footer.html";
?>
