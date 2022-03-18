package blackjack.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import blackjack.domain.*;
import blackjack.domain.card.CardShuffleMachine;
import blackjack.domain.card.Deck;
import blackjack.domain.card.PlayingCardShuffleMachine;
import blackjack.domain.player.Player;
import blackjack.domain.player.Players;
import blackjack.domain.result.Profits;
import blackjack.view.InputView;
import blackjack.view.OutputView;

public class BlackjackController {

    public void playGame() {
        List<String> playerNames = receivePlayerNames();
        HashMap<String, Integer> playersBetMoney = receivePlayersBetMoney(playerNames);
        BlackjackGame blackjackGame = BlackjackGame.of(Deck.create(), playerNames);
        CardShuffleMachine playingCardShuffleMachine = new PlayingCardShuffleMachine();
        blackjackGame.initGames(playingCardShuffleMachine);

        Players players = blackjackGame.getPlayers();
        startPlayTurn(blackjackGame, playingCardShuffleMachine, players);
        announceResult(blackjackGame, players, playersBetMoney);
    }

    private List<String> receivePlayerNames() {
        try {
            return InputView.inputPlayerNames();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return receivePlayerNames();
        }
    }

    private HashMap<String, Integer> receivePlayersBetMoney(List<String> playerNames) {
        HashMap<String, Integer> playersBetMoney = new LinkedHashMap<>();
        for (String name : playerNames) {
            playersBetMoney.put(name, receivePlayerBetMoney(name));
        }
        return playersBetMoney;
    }

    private int receivePlayerBetMoney(String name) {
        try {
            return InputView.inputBetMoney(name);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return receivePlayerBetMoney(name);
        }
    }

    private void startPlayTurn(BlackjackGame blackjackGame, CardShuffleMachine playingCardShuffleMachine, Players players) {
        announceStartGame(blackjackGame, players);
        turnPlayers(blackjackGame, playingCardShuffleMachine);
        turnDealer(blackjackGame, playingCardShuffleMachine);
    }

    private void announceStartGame(BlackjackGame blackjackGame, Players players) {
        OutputView.announceStartGame(players.getPlayers()
                .stream()
                .map(Player::getName)
                .collect(Collectors.toList()));
        OutputView.announcePresentCards(blackjackGame.getPlayersGameResponses());
    }

    private void turnPlayers(BlackjackGame blackjackGame, CardShuffleMachine playingCardShuffleMachine) {
        while (blackjackGame.isExistNextPlayer()) {
            turnGuest(blackjackGame, playingCardShuffleMachine);
        }
    }

    private void turnGuest(BlackjackGame blackjackGame, CardShuffleMachine playingCardShuffleMachine) {
        while (blackjackGame.isTurnGuest() && receiveHit(blackjackGame.getTurnPlayer().getName())) {
            blackjackGame.assignCard(blackjackGame.getTurnPlayer(), playingCardShuffleMachine);
            announcePresentCard(blackjackGame);
        }
        blackjackGame.nextTurn();
    }

    private boolean receiveHit(String name) {
        try {
            return InputView.requestHit(name);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return receiveHit(name);
    }

    private void turnDealer(BlackjackGame blackjackGame, CardShuffleMachine playingCardShuffleMachine) {
        while (blackjackGame.isTurnDealer()) {
            blackjackGame.assignCard(blackjackGame.getDealer(), playingCardShuffleMachine);
            OutputView.announceHit();
        }
    }

    private void announcePresentCard(BlackjackGame blackjackGame) {
        OutputView.announcePresentCards(blackjackGame.getTurnPlayerGameResponse());
    }

    private void announceResult(BlackjackGame blackjackGame, Players players, HashMap<String, Integer> playersBetMoney) {
        OutputView.announceResultCards(blackjackGame.getPlayersGameResponses());
        Profits profits = blackjackGame.calculateResult(players, playersBetMoney);
        OutputView.announceResultProfit(profits);
    }
}
