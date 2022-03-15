package blackjack.domain.player;

import blackjack.domain.card.PlayingCard;
import blackjack.domain.card.PlayingCards;

public abstract class AbstractPlayer implements Player {

    protected String name;
    protected PlayingCards playingCards;

    protected AbstractPlayer(String name, PlayingCards playingCards) {
        this.name = name;
        this.playingCards = playingCards;
    }

    @Override
    public void addCard(PlayingCard playingCard) {
        playingCards.addCard(playingCard);
    }

    @Override
    public boolean isBlackJack() {
        return playingCards.isBlackJack();
    }

    @Override
    public boolean isLose(int point) {
        return point > playingCards.sumPoints();
    }

    @Override
    public boolean isBust() {
        return playingCards.isBust();
    }

    @Override
    public boolean isDraw(Player player) {
        return playingCards.sumPoints() == player.getPlayingCards().sumPoints();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayingCards getPlayingCards() {
        return playingCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractPlayer that = (AbstractPlayer) o;

        return playingCards != null ? playingCards.equals(that.playingCards) : that.playingCards == null;
    }

    @Override
    public int hashCode() {
        return playingCards != null ? playingCards.hashCode() : 0;
    }
}
