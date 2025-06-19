package br.com.dio.hangman.model;

import br.com.dio.hangman.exception.GameIsFinishedException;
import br.com.dio.hangman.exception.LetterAlreadyInputtedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static br.com.dio.hangman.model.HangmanGameStatus.*;

public class HangmanGame {

    private final static int HANGMAN_INITIAL_LINE_LENGTH = 9;
    private final static int HANGMAN_INITIAL_LINE_LENGTH_WITH_LINE_SEPARATOR = 10;

    private final int lineSize;
    private final int hangmanInitialSize;
    private final List<HangmanChar> hangmanPaths;
    private final List<HangmanChar> characters;
    private final List<Character> failAttempts = new ArrayList<>();

    private String hangman;
    private HangmanGameStatus hangmanGameStatus;

    public HangmanGame(final List<HangmanChar> characters) {
        var whiteSpaces = " ".repeat(characters.size());
        var characterSpace = "-".repeat(characters.size());
        this.lineSize = HANGMAN_INITIAL_LINE_LENGTH_WITH_LINE_SEPARATOR + whiteSpaces.length();
        this.hangmanGameStatus = PENDING;
        this.hangmanPaths = buildHangmanPathsPositions();
        buildHangmanDesign(whiteSpaces, characterSpace);
        // ERRO CORRIGIDO: Adicionado operador de atribuição =
        this.characters = setCharacterSpacesPositionInGame(characters, whiteSpaces.length());
        this.hangmanInitialSize = hangman.length();
    }

    public void inputCharacter(final char character){
        if (this.hangmanGameStatus != PENDING){
            var message = this.hangmanGameStatus == WIN ?
                    "Parabéns você ganhou!" :
                    "Você perdeu, tente novamente";
            throw new GameIsFinishedException(message);
        }

        var found = this.characters.stream()
                .filter(c -> c.getCharacter() == character)
                .toList();

        if (this.failAttempts.contains(character)) {
            throw new LetterAlreadyInputtedException("A letra '"+ character + "' já foi informada anteriomente");
        }

        if (found.isEmpty()){
            failAttempts.add(character);
            if (failAttempts.size() >= 6){
                this.hangmanGameStatus = LOSE;
            }
            rebuildHangman(this.hangmanPaths.removeFirst());
            return;
        }

        if (found.getFirst().isVisible()){
            throw new LetterAlreadyInputtedException("A letra '"+ character + "' já foi informada anteriomente");
        }


        this.characters.forEach(c -> {
            if (c.getCharacter() == found.getFirst().getCharacter()){
                c.enableVisibility();
            }
        });

        if (this.characters.stream().noneMatch(HangmanChar::isInvisible)){
            this.hangmanGameStatus = WIN;
        }

        // ERRO CORRIGIDO: Sintaxe do toArray corrigida
        rebuildHangman(found.toArray(new HangmanChar[0]));
    }

    @Override
    public String toString() {
        return this.hangman;
    }

    private List<HangmanChar> buildHangmanPathsPositions(){
        final var HEAD_LINE = 3;  // Mudou de 2 para 3
        final var BODY_LINE = 4;  // Mudou de 3 para 4
        final var LEGS_LINE = 5;  // Mudou de 4 para 5

        return new ArrayList<>(
                List.of(
                        // Cabeça centralizada na forca
                        new HangmanChar('O', HEAD_LINE * this.lineSize + 6),
                        // Corpo
                        new HangmanChar('|', BODY_LINE * this.lineSize + 6),
                        // Braços
                        new HangmanChar('/', BODY_LINE * this.lineSize + 5),
                        new HangmanChar('\\', BODY_LINE * this.lineSize + 7),
                        // Pernas
                        new HangmanChar('/', LEGS_LINE * this.lineSize + 5),
                        new HangmanChar('\\', LEGS_LINE * this.lineSize + 7)
                )
        );
    }

    private List<HangmanChar> setCharacterSpacesPositionInGame(final List<HangmanChar> characters, final int whiteSpacesAmount){
        final var LINE_LETTER = 6;
        for (int i = 0; i < characters.size(); i++){
            characters.get(i).setPosition(this.lineSize * LINE_LETTER + HANGMAN_INITIAL_LINE_LENGTH + i);
        }
        return characters;
    }

    private void rebuildHangman(final HangmanChar... hangmanChars) {
        var hangmanBuilder = new StringBuilder(this.hangman);
        Stream.of(hangmanChars).forEach(
                h -> hangmanBuilder.setCharAt(h.getPosition(), h.getCharacter())
        );
        var failMessage = this.failAttempts.isEmpty() ? "" : "Tentativas" + this.failAttempts;
        this.hangman = hangmanBuilder.substring(0, hangmanInitialSize) + failMessage;
    }

    private void buildHangmanDesign(final String whiteSpaces, final String characterSpaces){
        this.hangman = " -----  " + whiteSpaces + System.lineSeparator() +
                       " |    | " + whiteSpaces + System.lineSeparator() +
                       " |    | " + whiteSpaces + System.lineSeparator() +
                       " |      " + whiteSpaces + System.lineSeparator() +
                       " |      " + whiteSpaces + System.lineSeparator() +
                       " |      " + whiteSpaces + System.lineSeparator() +
                       " |      " + whiteSpaces + System.lineSeparator() +
                       "=========" + characterSpaces + System.lineSeparator();
    }
}
