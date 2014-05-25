package DiezeFond;

import java.util.logging.MemoryHandler;

@SuppressWarnings("all")
public abstract class RobotMemory {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public MemoryHandler memoryHandler();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends RobotMemory.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements RobotMemory.Component, RobotMemory.Parts {
    private final RobotMemory.Requires bridge;
    
    private final RobotMemory implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.memoryHandler == null: "This is a bug.";
      this.memoryHandler = this.implementation.make_memoryHandler();
      if (this.memoryHandler == null) {
      	throw new RuntimeException("make_memoryHandler() in DiezeFond.RobotMemory should not return null.");
      }
      
    }
    
    public ComponentImpl(final RobotMemory implem, final RobotMemory.Requires b, final boolean doInits) {
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
    
    private MemoryHandler memoryHandler;
    
    public final MemoryHandler memoryHandler() {
      return this.memoryHandler;
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
  
  private RobotMemory.ComponentImpl selfComponent;
  
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
  protected RobotMemory.Provides provides() {
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
  protected abstract MemoryHandler make_memoryHandler();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected RobotMemory.Requires requires() {
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
  protected RobotMemory.Parts parts() {
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
  public synchronized RobotMemory.Component _newComponent(final RobotMemory.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of RobotMemory has already been used to create a component, use another one.");
    }
    this.init = true;
    RobotMemory.ComponentImpl comp = new RobotMemory.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public RobotMemory.Component newComponent() {
    return this._newComponent(new RobotMemory.Requires() {}, true);
  }
}
