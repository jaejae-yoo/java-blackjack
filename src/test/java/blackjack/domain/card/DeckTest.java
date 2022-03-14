package blackjack.domain.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeckTest {

    private Deck deck;

    @BeforeEach
    public void setUp() {
        deck = new Deck();
    }

    @Test
    @DisplayName("덱 객체 생성 확인")
    public void createDeck() {
        assertThat(deck).isInstanceOf(Deck.class);
    }

    @Test
    @DisplayName("deck cards에 card 더하는 로직 확인")
    public void checkAddCard() {
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.EIGHT));
        Deck comparedDeck = new Deck();
        comparedDeck.addCard(new PlayingCard(Suit.SPADE, Denomination.EIGHT));

        assertThat(deck).isEqualTo(comparedDeck);
    }

    @Test
    @DisplayName("deck card 2개일 때 점수 합산")
    public void checkSumDeckCardsPoint() {
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.EIGHT));
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.TWO));
        int sumPoint = deck.sumPoints();

        assertThat(sumPoint).isEqualTo(10);
    }

    @Test
    @DisplayName("deck card 3개일 때 점수 합산")
    public void checkSumDeckThreeCardsPoint() {
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.EIGHT));
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.TWO));
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.JACK));
        int sumPoint = deck.sumPoints();

        assertThat(sumPoint).isEqualTo(20);
    }

    @Test
    @DisplayName("ace 4개 일때 점수 확인")
    public void checkPointsForFourAces() {
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.CLUB, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.DIAMOND, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.HEART, Denomination.ACE));
        int sumPoint = deck.sumPoints();

        assertThat(sumPoint).isEqualTo(14);
    }

    @Test
    @DisplayName("ace 3개 일때 13점 점수 확인")
    public void checkPointsForThreeAces() {
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.CLUB, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.DIAMOND, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.DIAMOND, Denomination.TWO));
        int sumPoint = deck.sumPoints();

        assertThat(sumPoint).isEqualTo(15);
    }

    @Test
    @DisplayName("ace 2개 일때 12점 점수 확인")
    public void checkPointsForTwoAces() {
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.CLUB, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.DIAMOND, Denomination.NINE));
        int sumPoint = deck.sumPoints();

        assertThat(sumPoint).isEqualTo(21);
    }

    @Test
    @DisplayName("ace 2개 일때 2점 점수 확인")
    public void checkPointsForTwoAcesOverLimit() {
        deck.addCard(new PlayingCard(Suit.SPADE, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.CLUB, Denomination.ACE));
        deck.addCard(new PlayingCard(Suit.DIAMOND, Denomination.JACK));
        int sumPoint = deck.sumPoints();

        assertThat(sumPoint).isEqualTo(12);
    }
}
