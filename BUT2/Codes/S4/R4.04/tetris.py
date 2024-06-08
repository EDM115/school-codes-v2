import os
os.environ["PYGAME_HIDE_SUPPORT_PROMPT"] = "hide"
import pygame
import random
import sys


# Initialiser Pygame
pygame.init()

# Paramètres du jeu
SCREEN_WIDTH, SCREEN_HEIGHT = 500, 600
GRID_SIZE = 30
GRID_WIDTH, GRID_HEIGHT = 10, 20

# Couleurs
COLORS = [
    (0, 0, 0),
    (255, 0, 0),
    (0, 255, 0),
    (0, 0, 255),
    (255, 255, 0),
    (255, 165, 0),
    (128, 0, 128),
    (192, 192, 192)  # Couleur pour la prévisualisation
]

# Formes de Tetrominos
SHAPES = [
    [[1, 1, 1, 1]],  # Forme I
    [[1, 1],
     [1, 1]],  # Forme O
    [[0, 1, 0],
     [1, 1, 1]],  # Forme T
    [[0, 1, 1],
     [1, 1, 0]],  # Forme S
    [[1, 1, 0],
     [0, 1, 1]],  # Forme Z
    [[1, 0, 0],
     [1, 1, 1]],  # Forme J
    [[0, 0, 1],
     [1, 1, 1]]  # Forme L
]


# Classe pour gérer le Tetromino
class Tetromino:
    def __init__(self, shape):
        self.shape = shape
        self.color = random.randint(1, len(COLORS) - 2)
        self.x = GRID_WIDTH // 2 - len(shape[0]) // 2
        self.y = 0


    def rotate(self):
        self.shape = [list(row) for row in zip(*self.shape[::-1])]


# Initialiser la grille de jeu
grid = [[0 for _ in range(GRID_WIDTH)] for _ in range(GRID_HEIGHT)]


# Créer une nouvelle pièce
def new_tetromino():
    return Tetromino(random.choice(SHAPES))


# Fonction pour dessiner la grille et les pièces de Tetris
def draw_grid(surface, grid, tetromino, ghost_tetromino, next_tetromino, score, level, state):
    # Dessiner le cadre blanc autour de la zone de jeu
    pygame.draw.rect(surface, (255, 255, 255), (GRID_WIDTH * GRID_SIZE, 0, 2, GRID_HEIGHT * GRID_SIZE))
    
    # Dessiner chaque cellule de la grille avec les couleurs appropriées
    for y in range(GRID_HEIGHT):
        for x in range(GRID_WIDTH):
            color = COLORS[grid[y][x]]
            pygame.draw.rect(surface, color, (x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE))

    # Dessiner le fantôme de la pièce (ghost_tetromino) si disponible
    if ghost_tetromino:
        for y, row in enumerate(ghost_tetromino.shape):
            for x, value in enumerate(row):
                if value:
                    color = COLORS[-1]  # Couleur spéciale pour le fantôme
                    pygame.draw.rect(surface, color, ((ghost_tetromino.x + x) * GRID_SIZE, (ghost_tetromino.y + y) * GRID_SIZE, GRID_SIZE, GRID_SIZE))

    # Dessiner la pièce en cours (tetromino) si disponible
    if tetromino:
        for y, row in enumerate(tetromino.shape):
            for x, value in enumerate(row):
                if value:
                    color = COLORS[tetromino.color]
                    pygame.draw.rect(surface, color, ((tetromino.x + x) * GRID_SIZE, (tetromino.y + y) * GRID_SIZE, GRID_SIZE, GRID_SIZE))

    # Afficher le score actuel
    font = pygame.font.SysFont("Verdana", 24)
    score_text = font.render(f"Score : {score}", True, (255, 255, 255))
    surface.blit(score_text, (GRID_WIDTH * GRID_SIZE + 10, 10))
    level_text = font.render(f"Level : {level}", True, (255, 255, 255))
    surface.blit(level_text, (GRID_WIDTH * GRID_SIZE + 10, 40))

    # Afficher la prochaine pièce (next_tetromino) si disponible
    if next_tetromino:
        next_text = font.render("Prochain :", True, (255, 255, 255))
        surface.blit(next_text, (GRID_WIDTH * GRID_SIZE + 10, 70))
        for y, row in enumerate(next_tetromino.shape):
            for x, value in enumerate(row):
                if value:
                    color = COLORS[next_tetromino.color]
                    pygame.draw.rect(surface, color, (GRID_WIDTH * GRID_SIZE + 10 + x * GRID_SIZE, 100 + y * GRID_SIZE, GRID_SIZE, GRID_SIZE))

    # Afficher des messages spécifiques en fonction de l'état du jeu
    if state == "game_over":
        game_over_text = font.render("Game Over", True, (0, 0, 0), (255, 255, 255))
        surface.blit(game_over_text, (SCREEN_WIDTH // 2 - game_over_text.get_width() // 2, SCREEN_HEIGHT // 2 - game_over_text.get_height() // 2))
        restart_text = font.render("Appuyez sur R pour recommencer", True, (0, 0, 0), (255, 255, 255))
        surface.blit(restart_text, (SCREEN_WIDTH // 2 - restart_text.get_width() // 2, SCREEN_HEIGHT // 2 - restart_text.get_height() // 2 + 30))
        menu_text = font.render("Appuyez sur M pour le menu", True, (0, 0, 0), (255, 255, 255))
        surface.blit(menu_text, (SCREEN_WIDTH // 2 - menu_text.get_width() // 2, SCREEN_HEIGHT // 2 - menu_text.get_height() // 2 + 60))
    elif state == "pause":
        pause_text = font.render("Pause", True, (0, 0, 0), (255, 255, 255))
        surface.blit(pause_text, (SCREEN_WIDTH // 2 - pause_text.get_width() // 2, SCREEN_HEIGHT // 2 - pause_text.get_height() // 2))
        resume_text = font.render("Appuyez sur Echap pour reprendre", True, (0, 0, 0), (255, 255, 255))
        surface.blit(resume_text, (SCREEN_WIDTH // 2 - resume_text.get_width() // 2, SCREEN_HEIGHT // 2 - resume_text.get_height() // 2 + 30))
        restart_text = font.render("Appuyez sur R pour recommencer", True, (0, 0, 0), (255, 255, 255))
        surface.blit(restart_text, (SCREEN_WIDTH // 2 - restart_text.get_width() // 2, SCREEN_HEIGHT // 2 - restart_text.get_height() // 2 + 60))
        menu_text = font.render("Appuyez sur M pour le menu", True, (0, 0, 0), (255, 255, 255))
        surface.blit(menu_text, (SCREEN_WIDTH // 2 - menu_text.get_width() // 2, SCREEN_HEIGHT // 2 - menu_text.get_height() // 2 + 90))


# Fonction pour dessiner le menu principal
def draw_menu(surface):
    font = pygame.font.SysFont("Verdana", 24)
    # Texte du titre
    title_text = font.render("Tetris", True, (255, 255, 255))
    # Texte des options du menu
    start_text = font.render("Appuyez sur S pour commencer", True, (255, 255, 255))
    help_text = font.render("Appuyez sur H pour l'aide", True, (255, 255, 255))
    high_score_text = font.render("Appuyez sur C pour le score", True, (255, 255, 255))
    quit_text = font.render("Appuyez sur Q pour quitter", True, (255, 255, 255))
    # Positionnement du texte sur l'écran
    surface.blit(title_text, (SCREEN_WIDTH // 2 - title_text.get_width() // 2, SCREEN_HEIGHT // 2 - title_text.get_height() // 2 - 90))
    surface.blit(start_text, (SCREEN_WIDTH // 2 - start_text.get_width() // 2, SCREEN_HEIGHT // 2 - start_text.get_height() // 2 - 30))
    surface.blit(help_text, (SCREEN_WIDTH // 2 - help_text.get_width() // 2, SCREEN_HEIGHT // 2 - help_text.get_height() // 2))
    surface.blit(high_score_text, (SCREEN_WIDTH // 2 - high_score_text.get_width() // 2, SCREEN_HEIGHT // 2 - high_score_text.get_height() // 2 + 30))
    surface.blit(quit_text, (SCREEN_WIDTH // 2 - quit_text.get_width() // 2, SCREEN_HEIGHT // 2 - quit_text.get_height() // 2 + 60))


# Fonction pour dessiner l'écran d'aide
def draw_help(surface):
    font = pygame.font.SysFont("Verdana", 24)
    # Titre de l'écran d'aide
    help_title_text = font.render("Aide", True, (255, 255, 255))
    # Liste des textes d'aide
    help_texts = [
        "Flèche gauche : Déplacer à gauche",
        "Flèche droite : Déplacer à droite",
        "Flèche haut : Tourner",
        "Flèche bas : Descendre",
        "Espace : Descendre instantanément",
        "Echap : Pause",
        "M : Retour au menu",
        "R : Recommencer la partie"
    ]
    # Positionnement du titre sur l'écran
    surface.blit(help_title_text, (SCREEN_WIDTH // 2 - help_title_text.get_width() // 2, SCREEN_HEIGHT // 2 - help_title_text.get_height() // 2 - 120))

    # Positionnement des textes d'aide sur l'écran
    for i, text in enumerate(help_texts):
        help_text = font.render(text, True, (255, 255, 255))
        surface.blit(help_text, (SCREEN_WIDTH // 2 - help_text.get_width() // 2, SCREEN_HEIGHT // 2 - help_text.get_height() // 2 - 90 + 30 * i))


# Fonction pour dessiner l'écran de meilleur score
def draw_high_score(surface, high_score):
    font = pygame.font.SysFont("Verdana", 24)
    # Texte du titre
    title_text = font.render("Meilleur Score", True, (255, 255, 255))
    # Texte du score
    score_text = font.render(f"Score: {high_score}", True, (255, 255, 255))
    # Texte de retour au menu
    menu_text = font.render("Appuyez sur M pour le menu", True, (255, 255, 255))
    # Positionnement des textes sur l'écran
    surface.blit(title_text, (SCREEN_WIDTH // 2 - title_text.get_width() // 2, SCREEN_HEIGHT // 2 - title_text.get_height() // 2 - 30))
    surface.blit(score_text, (SCREEN_WIDTH // 2 - score_text.get_width() // 2, SCREEN_HEIGHT // 2 - score_text.get_height() // 2))
    surface.blit(menu_text, (SCREEN_WIDTH // 2 - menu_text.get_width() // 2, SCREEN_HEIGHT // 2 - menu_text.get_height() // 2 + 30))


# Vérifier les collisions entre le Tetromino et les limites ou les autres pièces de la grille
def check_collision(grid, tetromino):
    for y, row in enumerate(tetromino.shape):
        for x, value in enumerate(row):
            if value:  # Si la cellule fait partie du Tetromino
                try:
                    # Vérifier si le Tetromino dépasse les limites de la grille ou entre en collision avec une pièce existante
                    if y + tetromino.y >= GRID_HEIGHT or x + tetromino.x >= GRID_WIDTH or x + tetromino.x < 0 or grid[y + tetromino.y][x + tetromino.x]:
                        return True
                except IndexError:
                    # Gérer les cas où le Tetromino est hors des limites de la grille
                    return True
    return False  # Pas de collision détectée


# Fixer le Tetromino dans la grille lorsqu'il atteint sa position finale
def fix_tetromino(grid, tetromino):
    for y, row in enumerate(tetromino.shape):
        for x, value in enumerate(row):
            if value:  # Si la cellule fait partie du Tetromino
                # Copier la couleur du Tetromino dans la grille
                grid[y + tetromino.y][x + tetromino.x] = tetromino.color


# Supprimer les lignes complètes de la grille et décaler les lignes restantes
def clear_lines(grid, screen):
    cleared_lines = 0
    y = GRID_HEIGHT - 1
    while y >= 0:
        if 0 not in grid[y]:  # Si la ligne est complète
            for x in range(GRID_WIDTH):
                # Dessiner chaque cellule restante
                for row in range(GRID_HEIGHT):
                    for col in range(GRID_WIDTH):
                        if grid[row][col] != 0:
                            color = COLORS[grid[row][col]]
                            pygame.draw.rect(screen, color, (col * GRID_SIZE, row * GRID_SIZE, GRID_SIZE, GRID_SIZE))
                        else:
                            pygame.draw.rect(screen, (0, 0, 0), (col * GRID_SIZE, row * GRID_SIZE, GRID_SIZE, GRID_SIZE))
                pygame.display.update()

                # Faire disparaître chaque cube un par un
                pygame.draw.rect(screen, (0, 0, 0), (x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE))
                pygame.display.update()
                pygame.time.wait(50)  # Attendre 50ms entre chaque suppression de cellule

            del grid[y]  # Supprimer la ligne complète
            grid.insert(0, [0 for _ in range(GRID_WIDTH)])  # Ajouter une nouvelle ligne vide en haut de la grille
            cleared_lines += 1  # Incrémenter le compteur de lignes supprimées
            y += 1  # Réévaluer la même ligne
        y -= 1

    return cleared_lines  # Retourner le nombre de lignes supprimées


# Prévisualiser la position finale du Tetromino (ghost Tetromino)
def get_ghost_tetromino(grid, tetromino):
    ghost_tetromino = Tetromino(tetromino.shape)
    ghost_tetromino.color = -1  # Utiliser une couleur spéciale pour le ghost Tetromino
    ghost_tetromino.x = tetromino.x
    ghost_tetromino.y = tetromino.y

    # Descendre le ghost Tetromino jusqu'à la première collision
    while not check_collision(grid, ghost_tetromino):
        ghost_tetromino.y += 1

    ghost_tetromino.y -= 1  # Remonter d'une case après la collision

    return ghost_tetromino  # Retourner le ghost Tetromino


# Boucle principale du jeu
def main():
    screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))  # Initialiser la fenêtre de jeu
    clock = pygame.time.Clock()  # Créer un objet horloge pour gérer le temps
    fall_time = 0
    fall_speed = 500  # Vitesse de chute des Tetrominos en millisecondes
    level = 1
    score = 0
    high_score = 0
    state = "menu"  # État initial du jeu
    tetromino = None
    next_tetromino = None

    # Gestion des répétitions de touches
    key_delay = 500  # Délai initial avant de répéter une touche en millisecondes
    key_interval = 100  # Intervalle de répétition en millisecondes
    last_key_time = {pygame.K_LEFT: 0, pygame.K_RIGHT: 0, pygame.K_DOWN: 0, pygame.K_UP: 0, pygame.K_SPACE: 0}
    keys_held = {pygame.K_LEFT: False, pygame.K_RIGHT: False, pygame.K_DOWN: False, pygame.K_UP: False, pygame.K_SPACE: False}
    keys_initial = {pygame.K_LEFT: False, pygame.K_RIGHT: False, pygame.K_DOWN: False, pygame.K_UP: False, pygame.K_SPACE: False}

    while True:  # Boucle infinie du jeu
        screen.fill((0, 0, 0))  # Effacer l'écran
        fall_time += clock.get_rawtime()  # Temps écoulé depuis le dernier tick
        clock.tick()  # Mettre à jour l'horloge
        
        keys = pygame.key.get_pressed()  # Obtenir l'état des touches
        current_time = pygame.time.get_ticks()  # Obtenir le temps actuel

        for event in pygame.event.get():  # Parcourir les événements
            if event.type == pygame.QUIT:  # Quitter le jeu
                pygame.quit()
                sys.exit()
            elif event.type == pygame.KEYDOWN:  # Appui sur une touche
                if event.key in keys_held:
                    if not keys_held[event.key]:
                        keys_initial[event.key] = True
                        last_key_time[event.key] = current_time
                    keys_held[event.key] = True
                if state == "menu":
                    if event.key == pygame.K_s:  # Démarrer le jeu
                        tetromino = new_tetromino()
                        next_tetromino = new_tetromino()
                        grid = [[0 for _ in range(GRID_WIDTH)] for _ in range(GRID_HEIGHT)]
                        score = 0
                        fall_speed = 500
                        level = 1
                        state = "playing"
                    elif event.key == pygame.K_h:  # Afficher l'aide
                        state = "help"
                    elif event.key == pygame.K_c:  # Afficher le score
                        state = "high_score"
                    elif event.key == pygame.K_q:  # Quitter le jeu
                        pygame.quit()
                        sys.exit()
                elif state == "help":
                    if event.key == pygame.K_m:  # Retourner au menu
                        state = "menu"
                elif state == "high_score":
                    if event.key == pygame.K_m:  # Retourner au menu
                        state = "menu"
                elif state == "game_over":
                    if event.key == pygame.K_r:  # Recommencer la partie
                        tetromino = new_tetromino()
                        next_tetromino = new_tetromino()
                        grid = [[0 for _ in range(GRID_WIDTH)] for _ in range(GRID_HEIGHT)]
                        score = 0
                        fall_speed = 500
                        level = 1
                        state = "playing"
                    elif event.key == pygame.K_m:  # Retourner au menu
                        state = "menu"
                elif state == "pause":
                    if event.key == pygame.K_ESCAPE:  # Reprendre le jeu
                        state = "playing"
                    elif event.key == pygame.K_r:  # Recommencer la partie
                        tetromino = new_tetromino()
                        next_tetromino = new_tetromino()
                        grid = [[0 for _ in range(GRID_WIDTH)] for _ in range(GRID_HEIGHT)]
                        score = 0
                        fall_speed = 500
                        level = 1
                        state = "playing"
                    elif event.key == pygame.K_m:  # Retourner au menu
                        state = "menu"
                elif state == "playing":
                    if event.key == pygame.K_ESCAPE:  # Mettre en pause
                        state = "pause"

            elif event.type == pygame.KEYUP:  # Relâchement d'une touche
                if event.key in keys_held:
                    keys_held[event.key] = False
                    keys_initial[event.key] = False

        if state == "menu":
            draw_menu(screen)  # Dessiner le menu principal
        elif state == "help":
            draw_help(screen)  # Dessiner l'écran d'aide
        elif state == "high_score":
            draw_high_score(screen, high_score)  # Dessiner l'écran du meilleur score
        elif state == "game_over":
            draw_grid(screen, grid, tetromino, None, next_tetromino, score, level, state)  # Dessiner l'écran de game over
        elif state == "pause":
            draw_grid(screen, grid, tetromino, None, next_tetromino, score, level, state)  # Dessiner l'écran de pause
        elif state == "playing":
            if fall_time >= fall_speed:  # Si le temps de chute est écoulé
                tetromino.y += 1  # Faire descendre le Tetromino
                if check_collision(grid, tetromino):  # Vérifier les collisions
                    tetromino.y -= 1
                    fix_tetromino(grid, tetromino)  # Fixer le Tetromino dans la grille
                    cleared_lines = clear_lines(grid, screen)  # Supprimer les lignes complètes
                    score += 10  # Points pour chaque Tetromino posé
                    if cleared_lines > 0:
                        score += (cleared_lines ** 2) * 100  # Points bonus pour les lignes supprimées
                    tetromino = next_tetromino
                    next_tetromino = new_tetromino()
                    if check_collision(grid, tetromino):  # Vérifier les collisions avec le nouveau Tetromino
                        state = "game_over"
                        if score > high_score:  # Mettre à jour le meilleur score
                            high_score = score
                fall_time = 0  # Réinitialiser le temps de chute

            for key, held in keys_held.items():
                if held:  # Si la touche est maintenue enfoncée
                    if keys_initial[key]:  # Si c'est la première pression
                        keys_initial[key] = False
                        # Exécuter l'action immédiatement lors de la première pression
                        if key == pygame.K_LEFT:  # Déplacer à gauche
                            tetromino.x -= 1
                            if check_collision(grid, tetromino):
                                tetromino.x += 1
                        elif key == pygame.K_RIGHT:  # Déplacer à droite
                            tetromino.x += 1
                            if check_collision(grid, tetromino):
                                tetromino.x -= 1
                        elif key == pygame.K_DOWN:  # Descendre
                            tetromino.y += 1
                            if check_collision(grid, tetromino):
                                tetromino.y -= 1
                        elif key == pygame.K_UP:  # Tourner
                            tetromino.rotate()
                            # Vérifier les collisions après la rotation
                            if check_collision(grid, tetromino):
                                tetromino.rotate()
                                tetromino.rotate()
                                tetromino.rotate()
                        elif key == pygame.K_SPACE:  # Descendre instantanément
                            # Obtenir la position du ghost Tetromino et fixer le Tetromino dans la grille
                            ghost_tetromino = get_ghost_tetromino(grid, tetromino)
                            tetromino.x, tetromino.y = ghost_tetromino.x, ghost_tetromino.y
                            fix_tetromino(grid, tetromino)
                            # Mettre à jour le score en fonction du nombre de lignes supprimées
                            cleared_lines = clear_lines(grid, screen)
                            score += 10
                            if cleared_lines > 0:
                                score += (cleared_lines ** 2) * 100
                            tetromino = next_tetromino
                            next_tetromino = new_tetromino()
                            # Vérifier les collisions avec le nouveau Tetromino
                            if check_collision(grid, tetromino):
                                state = "game_over"
                                if score > high_score:
                                    high_score = score

                    else:  # Si la touche est maintenue enfoncée et que le délai est écoulé
                        if current_time - last_key_time[key] >= (key_interval if current_time - last_key_time[key] > key_delay else key_delay):
                            last_key_time[key] = current_time
                            if key == pygame.K_LEFT:  # Déplacer à gauche
                                tetromino.x -= 1
                                if check_collision(grid, tetromino):
                                    tetromino.x += 1
                            elif key == pygame.K_RIGHT:  # Déplacer à droite
                                tetromino.x += 1
                                if check_collision(grid, tetromino):
                                    tetromino.x -= 1
                            elif key == pygame.K_DOWN:  # Descendre
                                tetromino.y += 1
                                if check_collision(grid, tetromino):
                                    tetromino.y -= 1
                            elif key == pygame.K_UP:  # Tourner
                                tetromino.rotate()
                                # Vérifier les collisions après la rotation
                                if check_collision(grid, tetromino):
                                    tetromino.rotate()
                                    tetromino.rotate()
                                    tetromino.rotate()
                            elif key == pygame.K_SPACE:  # Descendre instantanément
                                # Obtenir la position du ghost Tetromino et fixer le Tetromino dans la grille
                                ghost_tetromino = get_ghost_tetromino(grid, tetromino)
                                tetromino.x, tetromino.y = ghost_tetromino.x, ghost_tetromino.y
                                fix_tetromino(grid, tetromino)
                                # Mettre à jour le score en fonction du nombre de lignes supprimées
                                cleared_lines = clear_lines(grid, screen)
                                score += 10
                                if cleared_lines > 0:
                                    score += (cleared_lines ** 2) * 100
                                tetromino = next_tetromino
                                next_tetromino = new_tetromino()
                                # Vérifier les collisions avec le nouveau Tetromino
                                if check_collision(grid, tetromino):
                                    state = "game_over"
                                    if score > high_score:
                                        high_score = score

            ghost_tetromino = get_ghost_tetromino(grid, tetromino)  # Obtenir la position du ghost Tetromino
            draw_grid(screen, grid, tetromino, ghost_tetromino, next_tetromino, score, level, state)  # Dessiner la grille et les Tetrominos

            # Augmenter la difficulté en fonction du score
            if score // 1000 > level - 1:
                level += 1
                fall_speed = max(100, fall_speed - 50)  # Augmenter la vitesse de chute

        pygame.display.update()  # Mettre à jour l'affichage

if __name__ == "__main__":
    main()
