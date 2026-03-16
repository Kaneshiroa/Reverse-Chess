<div align="center">

# ♟️ Reverse-Chess
**A Modular Chess Engine with Inverted Gameplay Mechanics**

[![Java Version](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Build Tool](https://img.shields.io/badge/Build-Maven-blue.svg)](https://maven.apache.org/)
[![Workflow](https://img.shields.io/badge/Workflow-Git--Flow-green.svg)](https://github.com/Kaneshiroa/Reverse-Chess/pulls)



</div>

---

## Overview
Reverse-Chess is a custom software project built to explore the complexities of game state management and Object-Oriented Design. This project implements a "Losing Chess" variant, requiring a flexible and robust logic engine to handle non-traditional win conditions and movement constraints.

## Rules of Play
This project operates on a variant where the objective is to be the first player to have no legal moves remaining.

### Core Mechanics
* **Forced Capture:** If a player is able to capture an opponent's piece, they are required to do so. If multiple captures are possible, the player may choose which one to execute.
* **Winning Conditions:** A player wins by being the first to:
    1.  Be placed in a checkmate position.
    2.  Have all pieces removed from the board (excluding the King, depending on configuration).
    3.  Reach a stalemate where no legal moves are available.

> **Note:** This project is currently in **active development**. We utilize professional software engineering workflows to ensure high-quality code and maintainability.

---

## Technical Architecture
This project is built from the ground up using **Object-Oriented Programming (OOP)** principles to ensure each component is decoupled and testable.

* **Core Logic:** Implemented a base `Piece` class with inheritance for specialized movement.
* **Coordinate Engine:** Custom `Vector2D` math for O(1) board positioning.
* **State Management:** Tracking piece history (e.g., `hasMoved`) to support future complex moves like **Castling** and **Pawn Promotion**.
* **GUI Interface:** Developed using **Java Swing** for real-time visual feedback of the board state.



---

## Project Roadmap & Progress

The development of **Reverse-Chess** is focused on creating a high-fidelity, two-player local experience with professional-grade UI/UX.

### **Phase 1: Core Engine (Completed)**
- [x] **Project Skeleton:** Maven setup and directory structure.
- [x] **Data Structures:** 2D Array-based board state and `Vector2D` coordinate utility.
- [x] **OOP Piece Architecture:** Base `Piece` class with inheritance for all 6 piece types.
- [x] **Movement Registry:** Tracking piece history (`hasMoved`) for specialized move validation.

### **Phase 2: Visuals & Asset Management (In Progress)**
- [x] **Sprite Integration:** Mapping high-resolution SVG/PNG chess piece assets to specific `Piece` subclasses.
- [ ] **Dynamic Board Rendering:** Implementing custom color themes (Classic Wood, Midnight, and High-Contrast).
- [x] **Asset Pipeline:** Efficient loading of images into the **Swing GUI** using `ImageIO` buffers for smooth rendering.

### **Phase 3: Advanced Game Logic & UX**
- [ ] **Promotion Engine:**  *Implementing transformation UI for Pawns reaching the 8th rank.*
- [ ] **Perspective Rotation:** Logic for rotating the board view 180° automatically based on the current player's turn.
- [ ] **Validation Engine:** Implementing Check/Checkmate detection and "En Passant" validation.

### **Phase 4: Game Polish**
- [ ] **Move History:** A visual log of PGN (Portable Game Notation) moves on the side of the GUI.
- [ ] **Sound Effects:** Triggering audio cues for captures, checks, and movement.

---

## Future Additions
* **Automated Opponent:** Implementation of a basic AI/Bot using heuristic evaluation.
* **Online Play:** Support for Socket-based multiplayer.

## Installation & Usage

### Prerequisites
* **JDK 17** or higher
* **Maven** (for dependency management)

### Running the App
```bash
# Clone the repository
git clone [https://github.com/Kaneshiroa/Reverse-Chess.git](https://github.com/Kaneshiroa/Reverse-Chess.git)

# Build the project
mvn clean install

# Launch the GUI
mvn exec:java -Dexec.mainClass="com.chess.Main"
