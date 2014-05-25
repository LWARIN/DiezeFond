package DiezeFond;

import fr.sma.speadl.EnvironmentUpdater;
import fr.sma.speadl.GridDataProvider;
import fr.sma.speadl.GridUpdater;

@SuppressWarnings("all")
public abstract class GridManager {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public EnvironmentUpdater updateEnvironment();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public GridUpdater updateGrid();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public GridDataProvider dataProvider();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends GridManager.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements GridManager.Component, GridManager.Parts {
    private final GridManager.Requires bridge;
    
    private final GridManager implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.updateEnvironment == null: "This is a bug.";
      this.updateEnvironment = this.implementation.make_updateEnvironment();
      if (this.updateEnvironment == null) {
      	throw new RuntimeException("make_updateEnvironment() in DiezeFond.GridManager should not return null.");
      }
      assert this.updateGrid == null: "This is a bug.";
      this.updateGrid = this.implementation.make_updateGrid();
      if (this.updateGrid == null) {
      	throw new RuntimeException("make_updateGrid() in DiezeFond.GridManager should not return null.");
      }
      assert this.dataProvider == null: "This is a bug.";
      this.dataProvider = this.implementation.make_dataProvider();
      if (this.dataProvider == null) {
      	throw new RuntimeException("make_dataProvider() in DiezeFond.GridManager should not return null.");
      }
      
    }
    
    public ComponentImpl(final GridManager implem, final GridManager.Requires b, final boolean doInits) {
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
    
    private EnvironmentUpdater updateEnvironment;
    
    public final EnvironmentUpdater updateEnvironment() {
      return this.updateEnvironment;
    }
    
    private GridUpdater updateGrid;
    
    public final GridUpdater updateGrid() {
      return this.updateGrid;
    }
    
    private GridDataProvider dataProvider;
    
    public final GridDataProvider dataProvider() {
      return this.dataProvider;
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
  
  private GridManager.ComponentImpl selfComponent;
  
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
  protected GridManager.Provides provides() {
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
  protected abstract EnvironmentUpdater make_updateEnvironment();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract GridUpdater make_updateGrid();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract GridDataProvider make_dataProvider();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected GridManager.Requires requires() {
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
  protected GridManager.Parts parts() {
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
  public synchronized GridManager.Component _newComponent(final GridManager.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of GridManager has already been used to create a component, use another one.");
    }
    this.init = true;
    GridManager.ComponentImpl comp = new GridManager.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public GridManager.Component newComponent() {
    return this._newComponent(new GridManager.Requires() {}, true);
  }
}
