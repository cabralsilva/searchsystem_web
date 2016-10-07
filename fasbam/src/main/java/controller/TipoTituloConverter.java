package controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.TipoTitulo;

@FacesConverter(value = "tipoTituloConverter")    
//@FacesConverter(forClass = Idioma.class)
public class TipoTituloConverter implements Converter {
  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
      if (value != null && !value.isEmpty()) {
          return (TipoTitulo) uiComponent.getAttributes().get(value);
      }
      return null;
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
      if (value instanceof TipoTitulo) {
    	  TipoTitulo entity= (TipoTitulo) value;
          if (entity != null && entity instanceof TipoTitulo && entity.getCodigo() != null) {
              uiComponent.getAttributes().put( entity.getCodigo().toString(), entity);
              return entity.getCodigo().toString();
          }
      }
      return "";
  }
}
