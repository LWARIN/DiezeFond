package DiezeFond;

import fr.sma.speadl.ActionHandler;
import fr.sma.speadl.GridUpdater;
import fr.sma.speadl.MoveHandler;
import fr.sma.speadl.MoveTrigger;

@SuppressWarnings("all")
public abstract class EnvironmentMove {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ActionHandler actionHandler();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public GridUpdater updateGrid();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public MoveHandler moveHandler();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public MoveTrigger moveTrigger();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends EnvironmentMove.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements EnvironmentMove.Component, EnvironmentMove.Parts {
    private final EnvironmentMove.Requires bridge;
    
    private final EnvironmentMove implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.moveHandler == null: "This is a bug.";
      this.moveHandler = this.implementation.make_moveHandler();
      if (this.moveHandler == null) {
      	throw new RuntimeException("make_moveHandler() in DiezeFond.EnvironmentMove should not return null.");
      }
      assert this.moveTrigger == null: "This is a bug.";
      this.moveTrigger = this.implementation.make_moveTrigger();
      if (this.moveTrigger == null) {
      	throw new RuntimeException("make_moveTrigger() in DiezeFond.EnvironmentMove should not return null.");
      }
      
    }
    
    public ComponentImpl(final EnvironmentMove implem, final EnvironmentMove.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
      
    }
    
    private MoveHandler moveHandler;
    
    public final MoveHandler moveHandler() {
      return this.moveHandler;
    }
    
    private MoveTrigger moveTrigger;
    
    public final MoveTrigger moveTrigger() {
      return this.moveTrigger;
    }
  }
  
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   */
  private boolean started = false;;
  
  private EnvironmentMove.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
    
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected EnvironmentMove.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract MoveHandler make_moveHandler();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract MoveTrigger make_moveTrigger();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected EnvironmentMove.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected EnvironmentMove.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized EnvironmentMove.Component _newComponent(final EnvironmentMove.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EnvironmentMove has already been used to create a component, use another one.");
    }
    this.init = true;
    EnvironmentMove.ComponentImpl comp = new EnvironmentMove.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
