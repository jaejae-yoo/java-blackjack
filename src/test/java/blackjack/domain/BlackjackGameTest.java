package blackjack.domain;

import blackjack.domain.card.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.domain.player.Dealer;
import blackjack.domain.player.Guest;
import blackjack.domain.player.Player;
import blackjack.domain.player.Players;
import blackjack.domain.result.Match;
import blackjack.domain.result.MatchResult;
import blackjack.domain.result.Results;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BlackjackGameTest {

    BlackjackGame blackjackGame;
    List<Player> players;
    Guest guest;
    Dealer dealer;

    @BeforeEach
    void setUp() {
        blackjackGame = new BlackjackGame();
        players = new ArrayList<>();
        guest = new Guest("haha");
        dealer = new Dealer();
        players.add(guest);
        players.add(dealer);
    }

    @Test
    @DisplayName("딜러가 블랙잭인 경우 확인")
    void isDealerBlackjack() {
        Players playList = new Players(players);
        guest.addCard(new PlayingCard(Suit.SPADE, Denomination.EIGHT));
        guest.addCard(new PlayingCard(Suit.SPADE, Denomination.SEVEN));
        playList.addPlayer(guest);

        dealer.addCard(new PlayingCard(Suit.DIAMOND, Denomination.ACE));
        dealer.addCard(new PlayingCard(Suit.DIAMOND, Denomination.TEN));
        playList.addPlayer(dealer);

        Results results = blackjackGame.calculateResult(playList);
        MatchResult guestResult = results.getResult(guest);
        MatchResult dealerResult = results.getResult(dealer);

        assertThat(guestResult.getMatch().get(Match.LOSE)).isEqualTo(dealerResult.getMatch().get(Match.WIN));
    }

    @Test
    @DisplayName("플레이어가 블랙잭인 경우 확인")
    void isPlayerBlackjack() {
        Players playerList = new Players(players);
        guest.addCard(new PlayingCard(Suit.SPADE, Denomination.ACE));
        guest.addCard(new PlayingCard(Suit.SPADE, Denomination.TEN));
        playerList.addPlayer(guest);

        dealer.addCard(new PlayingCard(Suit.DIAMOND, Denomination.ACE));
        dealer.addCard(new PlayingCard(Suit.DIAMOND, Denomination.THREE));
        dealer.addCard(new PlayingCard(Suit.DIAMOND, Denomination.SEVEN));
        playerList.addPlayer(dealer);

        Results results = blackjackGame.calculateResult(playerList);
        MatchResult guestResult = results.getResult(guest);
        MatchResult dealerResult = results.getResult(dealer);

        assertThat(guestResult.getMatch().get(Match.WIN)).isEqualTo(dealerResult.getMatch().get(Match.LOSE));
    }

    @Test
    @DisplayName("카드 뽑기 확인")
    void pickCard() {
        PlayingCardFixMachine playingCardFixMachine = new PlayingCardFixMachine();
        blackjackGame.addCard(guest, playingCardFixMachine);
        blackjackGame.addCard(guest, playingCardFixMachine);

        Set<PlayingCard> cards = new LinkedHashSet<>();
        cards.add(new PlayingCard(Suit.SPADE, Denomination.ACE));
        cards.add(new PlayingCard(Suit.SPADE, Denomination.TWO));

        assertThat(guest.getDeck().getCards()).isEqualTo(cards);
    }
}