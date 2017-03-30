// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.util.sorted;

/**
 * This represents a single node within the tree.
 *
 * @param <K> The key used.
 * @param <V> The value used.
 * @since 2016/09/06
 */
class __Node__<K, V>
{
	/** The color, defaults to red. */
	private volatile boolean _isred =
		true;
		
	/** The key. */
	volatile K _key;
	
	/** The value. */
	volatile V _value;
	
	/** The node to the left. */
	volatile __Node__<K, V> _left;
	
	/** The node to the right. */
	volatile __Node__<K, V> _right;
	
	/** Previous key value. */
	volatile __Node__<K, V> _prev;
	
	/** Next key value. */
	volatile __Node__<K, V> _next;
	
	/**
	 * Initializes a node with no value.
	 *
	 * @param __k The key.
	 * @param __v The value.
	 * @since 2016/09/06
	 */
	__Node__(K __k, V __v)
	{
		// Set
		this._key = __k;
		this._value = __v;
	}
}

