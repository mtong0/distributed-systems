import java.rmi.*;
import java.math.BigInteger;
public interface Bumper extends Remote{
    public boolean bump() throws RemoteException;
    public BigInteger get() throws RemoteException;
}
