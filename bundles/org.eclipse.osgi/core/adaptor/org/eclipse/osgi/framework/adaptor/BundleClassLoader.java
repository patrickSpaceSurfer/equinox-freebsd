/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.osgi.framework.adaptor;

import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Enumeration;

/**
 * The BundleClassLoader interface is used by the Framework to load local 
 * classes and resources from a Bundle.  Classes that implement this
 * interface must extend java.lang.ClassLoader, either directly or by extending
 * a subclass of java.lang.ClassLoader.<p>
 * 
 * ClassLoaders that implement the <code>BundleClassLoader</code> interface
 * must use a <code>ClassLoaderDelegate</code> to delegate all class, resource
 * and native library lookups.
 * 
 * @see org.eclipse.osgi.framework.adaptor.BundleData#createClassLoader(ClassLoaderDelegate, ProtectionDomain, String[])
 */
public interface BundleClassLoader /*extends ClassLoader*/{

	/**
	 * Initializes the ClassLoader.  This is called after all currently resolved fragment
	 * bundles have been attached to the BundleClassLoader by the Framework.
	 * @throws BundleException
	 */
	public void initialize();

	/**
	 * Finds a local resource in the BundleClassLoader without
	 * consulting the delegate.
	 * @param resource the resource path to find.
	 * @return a URL to the resource or null if the resource does not exist.
	 */
	public URL findLocalResource(String resource);

	/**
	 * Finds all local resources in the BundleClassLoader with the specified
	 * path without consulting the delegate.
	 * @param classname
	 * @return An Enumeration of all resources found or null if the resource.
	 * does not exist.
	 */
	public Enumeration findLocalResources(String resource);

	/**
	 * Finds a local class in the BundleClassLoader without
	 * consulting the delegate.
	 * @param classname the classname to find.
	 * @return The class object found.
	 * @throws ClassNotFoundException if the classname does not exist locally.
	 */
	public Class findLocalClass(String classname) throws ClassNotFoundException;

	/**
	 * Finds a local object in the BundleClassLoader without
	 * consulting the delegate.
	 * @param object the object name to fined.
	 * @return the object found or null if the object does not exist.
	 */
	public Object findLocalObject(String object);

	/**
	 * This method will first search the parent class loader for the resource;
	 * That failing, this method will invoke 
	 * {@link ClassLoaderDelegate#findResource(String)} to find the resource.   
	 * @param name the resource path to get.
	 * @return
	 */
	public URL getResource(String name);

	/**
	 * This method will first search the parent class loader for the class;
	 * That failing, this method will invoke 
	 * {@link ClassLoaderDelegate#findClass(String)} to find the resource.   
	 * @param name the class name to load.
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Class loadClass(String name) throws ClassNotFoundException;

	/**
	 * Closes this class loader.  After this method is called
	 * loadClass will always throw ClassNotFoundException,
	 * getResource, getResourceAsStream, getResources and will
	 * return null.
	 *
	 */
	public void close();

	/**
	 * Attaches the BundleData for a fragment to this BundleClassLoader.
	 * The Fragment BundleData resources must be appended to the end of
	 * this BundleClassLoader's classpath.  Fragment BundleData resources 
	 * must be searched ordered by Bundle ID's.  
	 * @param bundledata The BundleData of the fragment.
	 * @param domain The ProtectionDomain of the resources of the fragment.
	 * Any classes loaded from the fragment's BundleData must belong to this
	 * ProtectionDomain.
	 * @param classpath An array of Bundle-ClassPath entries to
	 * use for loading classes and resources.  This is specified by the 
	 * Bundle-ClassPath manifest entry of the fragment.
	 */
	abstract public void attachFragment(BundleData bundledata, ProtectionDomain domain, String[] classpath);

}

