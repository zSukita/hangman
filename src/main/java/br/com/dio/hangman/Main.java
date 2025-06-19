package br.com.dio.hangman;

import br.com.dio.hangman.model.HangmanChar;
import br.com.dio.hangman.model.HangmanGame;
import br.com.dio.hangman.exception.LetterAlreadyInputtedException;
import br.com.dio.hangman.exception.GameIsFinishedException;

import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("Nenhuma palavra foi fornecida para o jogo!");
            System.exit(1);
        }

        var word = args[0];
        var characters = word.toLowerCase().chars()
                .mapToObj(c -> (char) c)
                .map(HangmanChar::new)
                .toList();

        var hangmanGame = new HangmanGame(characters);
        System.out.println("Bem vindo ao jogo da forca, tente adivinhar a palavra, boa sorte");
        System.out.println(hangmanGame);
        while (true){
            System.out.println("Selecione uma das opções:");
            System.out.println("1 - Informar uma letra");
            System.out.println("2 - Verificar status do jogo");
            System.out.println("3 - Sair do jogo");
            var option = scanner.next();
            switch (option){
                case "1" -> inputCharacter(hangmanGame);
                case "2" -> showGameStatus(hangmanGame);
                case "3" -> System.exit(0);
                default -> System.out.println("Opção invalida");
            }

        }

    }

    private static void showGameStatus(HangmanGame hangmanGame) {
        System.out.println("Status: " + hangmanGame.getHangmanGameStatus());
        System.out.println(hangmanGame);
    }

    private static void inputCharacter(HangmanGame hangmanGame) {
        System.out.println("Informe uma letra");
        var character = scanner.next().charAt(0);
        try {
            hangmanGame.inputCharacter(character);
        }catch (LetterAlreadyInputtedException ex){
            System.out.println(ex.getMessage());
        } catch (GameIsFinishedException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        System.out.println(hangmanGame);
    }

}
