// business/PropertyService.java
package course_layers.business;

import course_layers.entities.Property;

import java.util.ArrayList;
import java.util.List;

public class PropertyService {

    // Simulación de datos base (se podría integrar luego con base de datos)
    public List<Property> obtenerPropiedadesBase() {
        List<Property> propiedades = new ArrayList<>();
        propiedades.add(new Property("EcoHostal", 2, 2));
        propiedades.add(new Property("Casa Miami", 3, 6));
        propiedades.add(new Property("Skyline View", 1, 4));
        return propiedades;
    }

    public Property buscarPorNombre(String nombre, List<Property> lista) {
        for (Property p : lista) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public boolean validarCapacidad(Property p, int numHuespedes) {
        return numHuespedes <= p.getCapacidad();
    }
}
