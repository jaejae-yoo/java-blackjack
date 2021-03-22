package blackjack.domain.state.finished;

import blackjack.domain.carddeck.Card;
import blackjack.domain.state.Started;
import blackjack.domain.state.State;
import blackjack.domain.state.hand.Hand;

public abstract class Finished extends Started {

    public Finished(final Hand hand) {
        super(hand);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public double profit(double money) {
        return money * earningRate();
    }

    @Override
    public State stay() {
        throw new UnsupportedOperationException();
    }

    @Override
    public State receiveCard(Card card) {
        throw new UnsupportedOperationException();
    }

    public abstract double earningRate();
}