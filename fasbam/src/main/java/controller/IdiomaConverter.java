package controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Idioma;

@FacesConverter(value = "idiomaConverter")    
//@FacesConverter(forClass = Idioma.class)
public class IdiomaConverter implements Converter {
  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
      if (value != null && !value.isEmpty()) {
          return (Idioma) uiComponent.getAttributes().get(value);
      }
      return null;
      
      
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
      if (value instanceof Idioma) {
    	  Idioma entity= (Idioma) value;
          if (entity != null && entity instanceof Idioma && entity.getCodigoIdioma() != null) {
              uiComponent.getAttributes().put( entity.getCodigoIdioma().toString(), entity);
              return entity.getCodigoIdioma().toString();
          }
      }
      return "";
  }
}
