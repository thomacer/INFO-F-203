public class NoResultFoundError extends RuntimeException {
    private Parking[] _best;
    public NoResultFoundError() {
        super();
    }

    public NoResultFoundError(Parking[] best){
        this._best = best;            
    }
    public Parking[] get_best() {
        return this._best; 
    }
}
