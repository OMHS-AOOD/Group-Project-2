package gamefiles;

import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Card> deck, pDistance, pSafety, pLimit, pBattle, cBattle, cLimit, cSafety, cDistance, discard, pHand, cHand;
	private String pName;
	private boolean hasDrawnCard, canCF, playedSafety;
	private int currentCardInt, pUsed200s, cUsed200s;
	private ArrayList<Integer> pWinCons, cWinCons;
	
	
	public GameData(MilleBornesGame mbg){
		pName = mbg.getPlayer().getName();
		hasDrawnCard = mbg.getBoard().getHasDrawnCard();
		canCF = mbg.getBoard().getCanCF();
		playedSafety = mbg.getBoard().getPlayedSafety();
		currentCardInt = mbg.getBoard().getCurrentCardInt();
		pUsed200s = mbg.getPlayer().getUsed200s();
		cUsed200s = mbg.getCPU().getUsed200s();
		pWinCons = mbg.getPlayer().getWinCons();
		cWinCons = mbg.getCPU().getWinCons();
		
		deck = mbg.getDeck().getStack();
		discard = mbg.getDiscard().getStack();
		pDistance = mbg.getPlayer().getDistance().getStack();
		pSafety = mbg.getPlayer().getSafety().getStack();
		pLimit = mbg.getPlayer().getLimit().getStack();
		pBattle = mbg.getPlayer().getBattle().getStack();
		cDistance = mbg.getCPU().getDistance().getStack();
		cSafety = mbg.getCPU().getSafety().getStack();
		cLimit = mbg.getCPU().getLimit().getStack();
		cBattle = mbg.getCPU().getBattle().getStack();

		pHand = mbg.getPlayer().getCards();
		cHand = mbg.getCPU().getCards();


	}


	public ArrayList<Card> getDeck() {
		return deck;
	}


	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}


	public ArrayList<Card> getpDistance() {
		return pDistance;
	}


	public void setpDistance(ArrayList<Card> pDistance) {
		this.pDistance = pDistance;
	}


	public ArrayList<Card> getpSafety() {
		return pSafety;
	}


	public void setpSafety(ArrayList<Card> pSafety) {
		this.pSafety = pSafety;
	}


	public ArrayList<Card> getpLimit() {
		return pLimit;
	}


	public void setpLimit(ArrayList<Card> pLimit) {
		this.pLimit = pLimit;
	}


	public ArrayList<Card> getpBattle() {
		return pBattle;
	}


	public void setpBattle(ArrayList<Card> pBattle) {
		this.pBattle = pBattle;
	}


	public ArrayList<Card> getcBattle() {
		return cBattle;
	}


	public void setcBattle(ArrayList<Card> cBattle) {
		this.cBattle = cBattle;
	}


	public ArrayList<Card> getcLimit() {
		return cLimit;
	}


	public void setcLimit(ArrayList<Card> cLimit) {
		this.cLimit = cLimit;
	}


	public ArrayList<Card> getcSafety() {
		return cSafety;
	}


	public void setcSafety(ArrayList<Card> cSafety) {
		this.cSafety = cSafety;
	}


	public ArrayList<Card> getcDistance() {
		return cDistance;
	}


	public void setcDistance(ArrayList<Card> cDistance) {
		this.cDistance = cDistance;
	}


	public ArrayList<Card> getDiscard() {
		return discard;
	}


	public void setDiscard(ArrayList<Card> discard) {
		this.discard = discard;
	}


	public ArrayList<Card> getpHand() {
		return pHand;
	}


	public void setpHand(ArrayList<Card> pHand) {
		this.pHand = pHand;
	}


	public ArrayList<Card> getcHand() {
		return cHand;
	}


	public void setcHand(ArrayList<Card> cHand) {
		this.cHand = cHand;
	}


	public String getpName() {
		return pName;
	}


	public void setpName(String pName) {
		this.pName = pName;
	}


	public boolean isHasDrawnCard() {
		return hasDrawnCard;
	}


	public void setHasDrawnCard(boolean hasDrawnCard) {
		this.hasDrawnCard = hasDrawnCard;
	}


	public boolean isCanCF() {
		return canCF;
	}


	public void setCanCF(boolean canCF) {
		this.canCF = canCF;
	}


	public boolean isPlayedSafety() {
		return playedSafety;
	}


	public void setPlayedSafety(boolean playedSafety) {
		this.playedSafety = playedSafety;
	}


	public int getCurrentCardInt() {
		return currentCardInt;
	}


	public void setCurrentCardInt(int currentCardInt) {
		this.currentCardInt = currentCardInt;
	}


	public int getpUsed200s() {
		return pUsed200s;
	}


	public void setpUsed200s(int pUsed200s) {
		this.pUsed200s = pUsed200s;
	}


	public int getcUsed200s() {
		return cUsed200s;
	}


	public void setcUsed200s(int cUsed200s) {
		this.cUsed200s = cUsed200s;
	}


	public ArrayList<Integer> getpWinCons() {
		return pWinCons;
	}


	public void setpWinCons(ArrayList<Integer> pWinCons) {
		this.pWinCons = pWinCons;
	}


	public ArrayList<Integer> getcWinCons() {
		return cWinCons;
	}


	public void setcWinCons(ArrayList<Integer> cWinCons) {
		this.cWinCons = cWinCons;
	}
}
