package DiezeFond;

import fr.sma.speadl.RobotHandler;

@SuppressWarnings("all")
public abstract class RobotFactory {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public RobotHandler robotHandler();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends RobotFactory.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements RobotFactory.Component, RobotFactory.Parts {
    private final RobotFactory.Requires bridge;
    
    private final RobotFactory implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.robotHandler == null: "This is a bug.";
      this.robotHandler = this.implementation.make_robotHandler();
      if (this.robotHandler == null) {
      	throw new RuntimeException("make_robotHandler() in DiezeFond.RobotFactory should not return null.");
      }
      
    }
    
    public ComponentImpl(final RobotFactory implem, final RobotFactory.Requires b, final boolean doInits) {
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
    
    private RobotHandler robotHandler;
    
    public final RobotHandler robotHandler() {
      return this.robotHandler;
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
  
  private RobotFactory.ComponentImpl selfComponent;
  
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
  protected RobotFactory.Provides provides() {
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
  protected abstract RobotHandler make_robotHandler();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected RobotFactory.Requires requires() {
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
  protected RobotFactory.Parts parts() {
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
  public synchronized RobotFactory.Component _newComponent(final RobotFactory.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of RobotFactory has already been used to create a component, use another one.");
    }
    this.init = true;
    RobotFactory.ComponentImpl comp = new RobotFactory.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public RobotFactory.Component newComponent() {
    return this._newComponent(new RobotFactory.Requires() {}, true);
  }
}
