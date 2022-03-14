package blackjack.domain;

import java.util.List;

import blackjack.domain.card.Cards;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.Guest;
import blackjack.domain.player.Player;
import blackjack.domain.player.Players;
import blackjack.domain.result.Results;

public class BlackjackGame {

    private final Cards cards;
    private Players blackjackPlayers;

    public BlackjackGame(Cards cards) {
        this.cards = cards;
    }

    public void initGames(List<String> playerNames) {
        initPlayers(playerNames);
        initCards();
    }

    private void initPlayers(List<String> playerNames) {
        blackjackPlayers = new Players();
        blackjackPlayers.addPlayer(new Dealer());
        for (String playerName : playerNames) {
            blackjackPlayers.addPlayer(new Guest(playerName));
        }
    }

    private void initCards() {
        for (Player blackjackPlayer : blackjackPlayers.getPlayers()) {
            blackjackPlayer.addCard(cards.assignCard());
            blackjackPlayer.addCard(cards.assignCard());
        }
    }

    public boolean isExistNextPlayer() {
        return blackjackPlayers.hasNextTurn();
    }

    public void nextTurn() {
        blackjackPlayers.nextTurn();
    }

    public boolean turnGuest() {
        Player player = blackjackPlayers.turnPlayer();
        if (player.isDealer()) {
            return false;
        }

        if (checkGetMoreCard(player)) {
            addCard(player);
            return true;
        }
        return false;
    }

    public boolean checkGetMoreCard(Player player) {
        return !player.isOverMoreCardLimit();
    }

    public boolean turnDealer() {
        Player dealer = blackjackPlayers.getDealer();
        if (!dealer.isOverMoreCardLimit()) {
            addCard(dealer);
            return true;
        }
        return false;
    }

    public void addCard(Player player) {
        player.addCard(cards.assignCard());
    }

    public Results calculateResult(Players players) {
        Results results = new Results(players);
        results.calculate();
        return results;
    }

    public Players getBlackjackPlayers() {
        return blackjackPlayers;
    }

    public Player getTurnPlayer() {
        return blackjackPlayers.turnPlayer();
    }
}
