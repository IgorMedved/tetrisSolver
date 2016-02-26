package model.listeners;

public interface ModelListener
{
	public void onStartTurn(int nextShapeType, boolean shouldUpdateType, int newScore, boolean shouldUpdateScore);
	public void onMove(String Message);
	public void onLineDelete();
}
