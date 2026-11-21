# Robo Rise

A 2D physics platformer written in Java. You play as **Tee**, a small robot escaping a factory. Collect energy credits, avoid spikes and beat the clock across three increasingly difficult levels.

Built with Java Swing and a JBox2D-based physics engine.

<!-- Add a screenshot or GIF of gameplay here, e.g. ![Gameplay](screenshots/gameplay.png) -->

## Gameplay

- **Goal:** collect 3 energy credits on each level to unlock the next one.
- **Timer:** you have 60 seconds per level. If time runs out, it's game over.
- **Health:** you have 5 hearts. Spikes cost 1 heart each.
- **Score:** your total credits across all levels are saved to a local top-5 leaderboard.

### Levels

| Level | Setting | What changes |
|-------|---------|--------------|
| 1 | Factory floor | Introduces platforms, spikes and a pushable crate you can use as a step |
| 2 | Underground mine | Tighter platforms, more spikes, harder-to-reach credits |
| 3 | Rooftop | Narrow platforms and spikes everywhere |

### Controls

| Key | Action |
|-----|--------|
| `A` | Move left |
| `D` | Move right |
| `W` | Jump |

## Features

- Physics-based movement, jumping and collisions
- Three levels with their own background and music
- Pushable crate that is locked to the ground so it can be used as a platform
- HUD with credit counter, heart-based health bar, level number and countdown timer
- Main menu, win and game-over screens, and a persistent high-score table
- Sound effects for jumping, collecting and taking damage, plus looping music per level

## How it's built

| Class | Responsibility |
|-------|----------------|
| `Game` | Entry point. Manages level loading and transitions, the timer, and the win and lose screens |
| `Level` (abstract) | Base class that creates the world and the player. Each level implements `populate()` |
| `Level1`, `Level2`, `Level3` | Place the platforms, hazards and credits for each stage |
| `Robot` | The player character: movement sprites, health and credits |
| `Controller` | Keyboard input (WASD) |
| `MyView` | Renders the world and draws the HUD on top |
| `CreditPickup`, `SpikeCollision` | Collision listeners for collecting credits and taking damage |
| `Crate`, `Spike`, `Credit`, `Platform` | Game objects with their own shapes and physics properties |
| `SoundManager` | Background music and one-shot sound effects (`javax.sound.sampled`) |
| `HighScoreManager`, `HighScoreScreen` | Saving and loading the top-5 scores, and the leaderboard screen |
| `MainMenu` | Start screen (Play, High Scores, Quit) |

Things I learned building it:

- Designing a level system with an **abstract base class**, so adding a level only means writing `populate()`
- Carrying player state (health and score) safely across level transitions
- Using physics step listeners for camera follow and for constraining the crate's movement
- Reading and writing files for persistent high scores
- Laying out Swing screens with `BorderLayout` and `BoxLayout`

## Running it

### Requirements

- Java 17 or newer
- `CityEngine.jar`, the 2D physics engine library (built on JBox2D) supplied with the City, University of London Java module. It isn't included in this repo.

### Steps

1. Clone the repo:
   ```bash
   git clone https://github.com/Tuam3/robo-rise.git
   cd robo-rise
   ```
2. Put `CityEngine.jar` in the `lib/` folder.
3. Compile and run from the project root, so the `data/` assets resolve:

   **Windows**
   ```powershell
   javac -cp "lib/*" -d out src/game/*.java
   java -cp "out;lib/*" game.Game
   ```

   **macOS / Linux**
   ```bash
   javac -cp "lib/*" -d out src/game/*.java
   java -cp "out:lib/*" game.Game
   ```

In IntelliJ IDEA, open the folder, mark `src` as the sources root, add `lib/CityEngine.jar` as a library, set the working directory to the project root and run `game.Game`.

## Project structure

```
robo-rise/
├── src/game/      Java source
├── data/          Sprites, backgrounds, music and sound effects
└── lib/           Put CityEngine.jar here (not committed)
```

## Credits

- Originally built as coursework for the Java programming module at City, University of London (2026).
- Game engine: the CityEngine teaching library from City, University of London, built on [JBox2D](https://github.com/jbox2d/jbox2d).
- Art and audio: royalty-free and free-to-use assets.

## License

The source code is released under the [MIT License](LICENSE). The CityEngine library and the third-party art and audio assets are covered by their own terms.
