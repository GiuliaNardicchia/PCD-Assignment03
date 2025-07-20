package it.unibo.pcd.assignment03.model;

import it.unibo.pcd.assignment03.view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteUpdateObserverImpl extends UnicastRemoteObject implements RemoteUpdateObserver {
    private final View localView;

    public RemoteUpdateObserverImpl(View localView) throws RemoteException {
        super();
        this.localView = localView;
    }

    @Override
    public void notifyUpdate() throws RemoteException {
        localView.refresh();
    }
}
