// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package com.oracle.json;

import cc.squirreljme.runtime.cldc.annotation.Api;

/**
 * This is a builder which is able to generate {@link JsonObject}s, it also
 * makes it possible to chain them together as successive object calls (most
 * of the methods return {@code this}.
 *
 * No key name or value must ever contain {@code null}.
 *
 * @since 2014/07/25
 */
@Api
@SuppressWarnings("InterfaceWithOnlyOneDirectInheritor")
public interface JsonObjectBuilder
{
	/**
	 * Adds either {@link JsonValue#TRUE} or {@link JsonValue#FALSE} and
	 * associates it with the given key.
	 *
	 * If the key is already set with a value, it is replaced.
	 *
	 * @param __n Name of the key pair to add.
	 * @param __v Value of the key pair to add.
	 * @return {@code this}.
	 * @throws NullPointerException If {@code __n} is {@code null}.
	 * @since 2014/07/25
	 */
	@Api
	JsonObjectBuilder add(String __n, boolean __v);
	
	/**
	 * Adds a {@link JsonNumber} with the specified value and associates it
	 * with the given key.
	 *
	 * If the key is already set with a value, it is replaced.
	 *
	 * @param __n Name of the key pair to add.
	 * @param __v Value of the key pair to add.
	 * @return {@code this}.
	 * @throws NullPointerException If {@code __n} is {@code null}.
	 * @since 2014/07/25
	 */
	@Api
	JsonObjectBuilder add(String __n, double __v);
	
	/**
	 * Adds a {@link JsonNumber} with the specified value and associates it
	 * with the given key.
	 *
	 * If the key is already set with a value, it is replaced.
	 *
	 * @param __n Name of the key pair to add.
	 * @param __v Value of the key pair to add.
	 * @return {@code this}.
	 * @throws NullPointerException If {@code __n} is {@code null}.
	 * @since 2014/07/25
	 */
	@Api
	JsonObjectBuilder add(String __n, int __v);
	
	/**
	 * Adds a {@link JsonArray} with an array which would be generated by the
	 * builder.
	 *
	 * If the key is already set with a value, it is replaced.
	 *
	 * @param __n Name of the key pair to add.
	 * @param __v Value of the key pair to add.
	 * @return {@code this}.
	 * @throws NullPointerException If {@code __n} or {@code __v} are
	 * {@code null}.
	 * @since 2014/07/25
	 */
	@Api
	JsonObjectBuilder add(String __n, JsonArrayBuilder __v);
	
	/**
	 * Adds a {@link JsonObject} with an object which would be generated by the
	 * builder.
	 *
	 * If the key is already set with a value, it is replaced.
	 *
	 * @param __n Name of the key pair to add.
	 * @param __v Value of the key pair to add.
	 * @return {@code this}.
	 * @throws NullPointerException If {@code __n} or {@code __v} are
	 * {@code null}.
	 * @since 2014/07/25
	 */
	@Api
	JsonObjectBuilder add(String __n, JsonObjectBuilder __v);
	
	/**
	 * Adds a {@link JsonValue} and associates it with the given key.
	 *
	 * If the key is already set with a value, it is replaced.
	 *
	 * @param __n Name of the key pair to add.
	 * @param __v Value of the key pair to add.
	 * @return {@code this}.
	 * @throws NullPointerException If {@code __n} or {@code __v} are
	 * {@code null}.
	 * @since 2014/07/25
	 */
	@Api
	JsonObjectBuilder add(String __n, JsonValue __v);
	
	/**
	 * Adds a {@link JsonNumber} with the specified value and associates it
	 * with the given key.
	 *
	 * If the key is already set with a value, it is replaced.
	 *
	 * @param __n Name of the key pair to add.
	 * @param __v Value of the key pair to add.
	 * @return {@code this}.
	 * @throws NullPointerException If {@code __n} is {@code null}.
	 * @since 2014/07/25
	 */
	@Api
	JsonObjectBuilder add(String __n, long __v);
	
	/**
	 * Adds a {@link JsonString} with the specified value and associates it
	 * with the given key.
	 *
	 * If the key is already set with a value, it is replaced.
	 *
	 * @param __n Name of the key pair to add.
	 * @param __v Value of the key pair to add.
	 * @return {@code this}.
	 * @throws NullPointerException If {@code __n} or {@code __v} are
	 * {@code null}.
	 * @since 2014/07/25
	 */
	@Api
	JsonObjectBuilder add(String __n, String __v);
	
	/**
	 * Adds {@link JsonValue#NULL} to the object with the specified key.
	 *
	 * If the key is already set with a value, it is replaced.
	 *
	 * @param __n Name of the key pair to add.
	 * @return {@code this}.
	 * @throws NullPointerException If {@code __n} is {@code null}.
	 * @since 2014/07/25
	 */
	@Api
	JsonObjectBuilder addNull(String __n);
	
	/**
	 * Returns the Json object associated with this builder, the iteration of
	 * the order matches the order keys were added in.
	 *
	 * @return The built Json object.
	 * @since 2014/07/24
	 */
	@Api
	JsonObject build();
}

