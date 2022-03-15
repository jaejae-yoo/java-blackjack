package blackjack.domain.player;

import blackjack.domain.card.PlayingCards;

public class Dealer extends AbstractPlayer implements Player {

    public static final String NAME = "딜러";
    public static final int MAX_POINT = 16;
    public static final int EXCEED_POINT = 17;

    public Dealer() {
        this.playingCards = new PlayingCards();
        this.name = NAME;
    }

    @Override
    public boolean isCanHit() {
        return playingCards.sumPoints() > MAX_POINT;
    }

    @Override
    public boolean isDealer() {
        return true;
    }

    @Override
    public boolean isWin(Player guest, Player dealer) {
        int points = playingCards.sumPoints();
        if (guest.isBlackJack() && !isBlackJack()) {
            return false;
        }
        if (guest.isBust() && points <= PlayingCards.BLACKJACK_POINT) {
            return true;
        }
        return (guest.isLose(points)) && (points <= PlayingCards.BLACKJACK_POINT);
    }
}
