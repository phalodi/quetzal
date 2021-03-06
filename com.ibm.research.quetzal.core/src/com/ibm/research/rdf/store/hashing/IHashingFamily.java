/******************************************************************************
 * Copyright (c) 2015 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *****************************************************************************/
 package com.ibm.research.rdf.store.hashing;

/**
 * Interface that offers a method to hash strings to integers.
 */
public interface IHashingFamily {
	/** Returns a hash of string s. */
	public int hash(String s, int HashId);
	// some hashing family sizes might be dependent on a specific predicate
	public int getFamilySize(String predicate);
	public void computeHash(String s);
	
	// Added for Single triple operations.
	public int[] getHash(String s);
}
