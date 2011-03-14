/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.modules.form;

import java.util.*;
import java.lang.reflect.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tomas Pavek
 */

public class CreationDescriptor {

  public interface Creator {

        public int getParameterCount();

        public Class[] getParameterTypes();

        public String[] getPropertyNames();

        public Object createInstance(Object[] paramValues)
            throws InstantiationException, IllegalAccessException,
                   IllegalArgumentException, InvocationTargetException;

    }

    private Class describedClass;
    private List<Creator> creators = new ArrayList<Creator>(10);
    private Object[] defaultParams;
    private Creator defaultCreator;

    private static final Class[] emptyTypes = { };
    private static final String[] emptyNames = { };
    private static final Object[] emptyParams = { };

    public CreationDescriptor() {        
    }

    public CreationDescriptor(Class descClass,
                              Class[][] constrParamTypes,
                              String[][] constrPropNames,
                              Object[] defParams)
    throws NoSuchMethodException, // if some constructor is not found            
           IllegalArgumentException 
    {   
        addConstructorCreators(descClass, constrParamTypes, constrPropNames, defParams);
    }
    
    CreationDescriptor(Class factoryClass, 
                              Class descClass,
                              String methodName,                                
                              Class[][] constrParamTypes,
                              String[][] constrPropNames,
                              CreationFactory.PropertyParameters[] propertyParameters,   
                              Object[] defParams)
    throws NoSuchMethodException, // if some method is not found                                                           
           IllegalArgumentException  
    {           
        addMethodCreators(factoryClass, descClass, methodName, constrParamTypes, 
                          constrPropNames, propertyParameters, defParams);
    }
    
    public void addConstructorCreators(Class descClass,
                                       Class[][] constrParamTypes,
                                       String[][] constrPropNames,
                                       Object[] defParams)
    throws NoSuchMethodException // if some constructor is not found
    {   
        if (getDescribedClass() == null) {
            setDescribedClass(descClass);
        } else if (getDescribedClass() != descClass) {
            throw new IllegalArgumentException();
        }
        if (constrParamTypes != null && constrParamTypes.length > 0) {
            
            for (int i=0; i < constrParamTypes.length; i++)
                creators.add( new ConstructorCreator(describedClass,
                                                         constrParamTypes[i],
                                                         constrPropNames[i]) );
        }

        defaultParams = defParams == null ? emptyParams : defParams;
    }

    void addMethodCreators(Class factoryClass, 
                                  Class descClass,
                                  String methodName,                                
                                  Class[][] constrParamTypes,
                                  String[][] constrPropNames,
                                  CreationFactory.PropertyParameters[] propertyParameters,   
                                  Object[] defParams)
    throws NoSuchMethodException // if some method is not found
    {
        if (getDescribedClass() == null) {
            setDescribedClass(descClass);
        } else if (getDescribedClass() != descClass) {
            throw new IllegalArgumentException();
        }
        
        if (constrParamTypes != null && constrParamTypes.length > 0) {
            
            CreationFactory.Property2ParametersMapper[] properties;
            for (int i=0; i < constrParamTypes.length; i++) {
                
                properties = new CreationFactory.Property2ParametersMapper[constrParamTypes[i].length];
                for (int j = 0; j < constrParamTypes[i].length; j++) {
                    properties[j] = new CreationFactory.Property2ParametersMapper(constrParamTypes[i][j], constrPropNames[i][j]);
                    if(propertyParameters != null && propertyParameters.length > 0) {
                      for (CreationFactory.PropertyParameters propertyParameter : propertyParameters)
                      {
                        if (propertyParameter.getPropertyName().equals(constrPropNames[i][j]))
                        {
                          properties[j].setPropertyParameters(propertyParameter);
                        }
                      }
                    }
                }               
                
                creators.add( new MethodCreator(factoryClass, 
                                               describedClass,                        
                                               methodName,
                                               properties));  
                
            }
            
        }      
        
        defaultParams = defParams == null ? emptyParams : defParams;
    }

  protected void setDescribedClass(Class descClass) throws IllegalArgumentException {
        if (describedClass==null) {
            describedClass = descClass;
        }
    }

    public CreationDescriptor(Class descClass) {
//        throws NoSuchMethodException // if public empty constructor doesn't exist
        describedClass = descClass;

        try {
            ConstructorCreator creator = new ConstructorCreator(describedClass,
                                                                emptyTypes,
                                                                emptyNames);
            creators.add( creator );
        }
        catch (NoSuchMethodException ex) { // ignore
            Logger.getLogger(CreationDescriptor.class.getName())
                    .log(Level.INFO, "[WARNING] No default constructor for "+descClass.getName(), ex); // NOI18N
        }

        defaultParams = emptyParams;
    }

    // ---------

    public Class getDescribedClass() {
        return describedClass;
    }
    
    /**
     * This method allows sub-classes to return name of the
     * described class without the need to load the class itself.
     *
     * @return name of the described class e.g. (<code>getDescribedClass().getName()</code>).
     */
    public String getDescribedClassName() {
        return getDescribedClass().getName();
    }

    public Creator[] getCreators() {
        return creators.toArray(new Creator[creators.size()]);
    }

  public Object createDefaultInstance() throws InstantiationException,
                                                 IllegalAccessException,
                                                 IllegalArgumentException,
                                                 InvocationTargetException,
                                                 NoSuchMethodException
    {
        return getDefaultCreator().createInstance(defaultParams);
    }

    private Creator getDefaultCreator() throws NoSuchMethodException {
        if( defaultCreator == null ) {
            defaultCreator = findDefaultCreator();
        } 
        return defaultCreator;
    }
    // ----------

    // finds first constructor that matches defaultConstrParams
    private Creator findDefaultCreator() throws NoSuchMethodException {
      for (Creator creator : creators)
      {

        Class[] paramTypes = creator.getParameterTypes();

        if (paramTypes.length == defaultParams.length)
        {
          int ii;
          for (ii = 0; ii < paramTypes.length; ii++)
          {
            Class cls = paramTypes[ii];
            Object param = defaultParams[ii];

            if (cls.isPrimitive())
            {
              if (param == null
                  || (param instanceof Integer && cls != Integer.TYPE)
                  || (param instanceof Boolean && cls != Boolean.TYPE)
                  || (param instanceof Double && cls != Double.TYPE)
                  || (param instanceof Long && cls != Long.TYPE)
                  || (param instanceof Float && cls != Float.TYPE)
                  || (param instanceof Short && cls != Short.TYPE)
                  || (param instanceof Byte && cls != Byte.TYPE)
                  || (param instanceof Character && cls != Character.TYPE))
                break;
            }
            else if (param != null && !cls.isInstance(param))
              break;
          }
          if (ii == paramTypes.length)
          {
            return creator;
          }
        }
      }
        throw new NoSuchMethodException();
    }

    // ----------

    static class ConstructorCreator implements Creator {
      private Constructor constructor;
//        private Class[] constructorParamTypes;
        private String[] constructorPropNames;

        ConstructorCreator(Class cls, Class[] paramTypes, String[] propNames)
            throws NoSuchMethodException
        {
            if (paramTypes == null)
                paramTypes = emptyTypes;
            if (propNames == null)
                propNames = emptyNames;
            if (paramTypes.length != propNames.length)
                throw new IllegalArgumentException();

            constructor = cls.getConstructor(paramTypes);
          //            constructorParamTypes = paramTypes;
            constructorPropNames = propNames;
        }

        @Override
        public final int getParameterCount() {
            return constructorPropNames.length; //constructorParamTypes.length;
        }

        @Override
        public final Class[] getParameterTypes() {
            return constructor.getParameterTypes(); //constructorParamTypes;
        }

      @Override
        public final String[] getPropertyNames() {
            return constructorPropNames;
        }

      @Override
        public Object createInstance(Object[] paramValues)
            throws InstantiationException, IllegalAccessException,
                   IllegalArgumentException, InvocationTargetException
        {           
            return constructor.newInstance(paramValues);
        }

    }
    
    static class MethodCreator implements Creator {
      private Class describedClass;
        private Method method;
      private String[] propertyNames;
        
        MethodCreator(Class factoryClass, Class describedClass, String methodName, CreationFactory.Property2ParametersMapper[] properties)
            throws NoSuchMethodException
        {            
                        
            List<Class> paramTypesList = new ArrayList<Class>();
            propertyNames = new String[properties.length];    
            
            for (int i = 0; i < properties.length; i++) {                                
                for (int j = 0; j < properties[i].getPropertyTypes().length; j++) {
                    paramTypesList.add(properties[i].getPropertyTypes()[j]);                                        
                }                                
                propertyNames[i] = properties[i].getPropertyName();
            }                       
                                    
            Class[] paramTypes = paramTypesList.toArray(new Class[paramTypesList.size()]);

            method = factoryClass.getMethod(methodName, paramTypes);

          this.describedClass = describedClass;

        }
    
            
        @Override
        public final int getParameterCount() {
            return propertyNames.length; 
        }

        @Override
        public final Class[] getParameterTypes() {
            return method.getParameterTypes(); 
        }

      @Override
        public final String[] getPropertyNames() {
            return propertyNames;
        }

      @Override
        public Object createInstance(Object[] paramValues)
            throws InstantiationException, IllegalAccessException,
                   IllegalArgumentException, InvocationTargetException
        {                                            
            
            Object ret = method.invoke(null, paramValues);
            if(ret.getClass() != describedClass) {                
                throw new IllegalArgumentException();
            }
            return ret;
        }

    }

}
