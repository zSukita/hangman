package br.com.dio.hangman;

import br.com.dio.hangman.model.HangmanChar;

import java.util.stream.Stream;

public class Main {

    public static void main(String... args) {
        var letter = Stream.of(args)
                .map(a ->a.toLowerCase().charAt(0))
                .map(HangmanChar::new).toList();

        System.out.println(letter);
    }


}
