package DiezeFond;

import fr.sma.speadl.EnvironmentRenderer;
import fr.sma.speadl.GridDataProvider;

@SuppressWarnings("all")
public abstract class EnvironmentGUI {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public GridDataProvider dataProvider();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public EnvironmentRenderer renderEnvironment();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends EnvironmentGUI.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements EnvironmentGUI.Component, EnvironmentGUI.Parts {
    private final EnvironmentGUI.Requires bridge;
    
    private final EnvironmentGUI implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.renderEnvironment == null: "This is a bug.";
      this.renderEnvironment = this.implementation.make_renderEnvironment();
      if (this.renderEnvironment == null) {
      	throw new RuntimeException("make_renderEnvironment() in DiezeFond.EnvironmentGUI should not return null.");
      }
      
    }
    
    public ComponentImpl(final EnvironmentGUI implem, final EnvironmentGUI.Requires b, final boolean doInits) {
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
    
    private EnvironmentRenderer renderEnvironment;
    
    public final EnvironmentRenderer renderEnvironment() {
      return this.renderEnvironment;
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
  
  private EnvironmentGUI.ComponentImpl selfComponent;
  
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
  protected EnvironmentGUI.Provides provides() {
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
  protected abstract EnvironmentRenderer make_renderEnvironment();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected EnvironmentGUI.Requires requires() {
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
  protected EnvironmentGUI.Parts parts() {
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
  public synchronized EnvironmentGUI.Component _newComponent(final EnvironmentGUI.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EnvironmentGUI has already been used to create a component, use another one.");
    }
    this.init = true;
    EnvironmentGUI.ComponentImpl comp = new EnvironmentGUI.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
