package blackjack.domain;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.domain.card.*;
import blackjack.domain.player.Guest;

import static org.assertj.core.api.Assertions.assertThat;

class BlackjackGameTest {


    @Test
    @DisplayName("카드가 할당되는지 확인")
    void pickCard() {
        Guest guest = new Guest("guest", new PlayingCards(), 100);
        PlayingCardFixMachine playingCardFixMachine = new PlayingCardFixMachine();
        HashMap<String, Integer> playersBetMoney = new HashMap<>();

        BlackjackGame blackjackGame = BlackjackGame.of(Deck.create(), new ArrayList<>(), playersBetMoney);
        blackjackGame.assignCard(guest, playingCardFixMachine);
        blackjackGame.assignCard(guest, playingCardFixMachine);

        assertThat(guest.getPlayingCards().getCards().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("다음 순서가 존재하지 않는 경우: 딜러만 있는 경우")
    void checkNonExistNextTurn() {
        List<String> players = new ArrayList<>();
        CardShuffleMachine playingCardShuffleMachine = new PlayingCardShuffleMachine();
        HashMap<String, Integer> playersBetMoney = new HashMap<>();

        BlackjackGame blackjackGame = BlackjackGame.of(Deck.create(), players, playersBetMoney);
        blackjackGame.initGames(playingCardShuffleMachine);
        blackjackGame.nextTurn();

        assertThat(blackjackGame.isExistNextPlayer()).isFalse();
    }

    @Test
    @DisplayName("다음 순서가 존재하는 경우: 플레이어가 있는 경우")
    void checkExistNextTurn() {
        List<String> players = new ArrayList<>();
        players.add("green");

        CardShuffleMachine playingCardShuffleMachine = new PlayingCardShuffleMachine();
        HashMap<String, Integer> playersBetMoney = new HashMap<>();
        playersBetMoney.put("green", 100);

        BlackjackGame blackjackGame = BlackjackGame.of(Deck.create(), players, playersBetMoney);
        blackjackGame.initGames(playingCardShuffleMachine);
        blackjackGame.nextTurn();

        assertThat(blackjackGame.isExistNextPlayer()).isTrue();
    }

    @Test
    @DisplayName("플레이어 턴이 맞는지 확인")
    void checkRightPlayerTurn() {
        List<String> players = new ArrayList<>();
        players.add("green");

        PlayingCardFixMachine playingCardFixMachine = new PlayingCardFixMachine();
        HashMap<String, Integer> playersBetMoney = new HashMap<>();
        playersBetMoney.put("green", 100);

        BlackjackGame blackjackGame = BlackjackGame.of(Deck.create(), players, playersBetMoney);
        blackjackGame.initGames(playingCardFixMachine);
        blackjackGame.nextTurn();

        assertThat(blackjackGame.isTurnGuest()).isTrue();
    }

    @Test
    @DisplayName("딜러 턴이 가능한지 확인")
    void checkPossibleDealerTurn() {
        List<String> players = new ArrayList<>();
        CardShuffleMachine playingCardFixMachine = new PlayingCardFixMachine();
        HashMap<String, Integer> playersBetMoney = new HashMap<>();

        BlackjackGame blackjackGame = BlackjackGame.of(Deck.create(), players, playersBetMoney);
        blackjackGame.initGames(playingCardFixMachine);

        assertThat(blackjackGame.isTurnDealer()).isFalse();
    }
}
