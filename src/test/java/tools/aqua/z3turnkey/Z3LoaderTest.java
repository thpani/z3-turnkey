/*
 * Copyright 2019-2022 The Z3-TurnKey Authors
 * SPDX-License-Identifier: ISC
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 * granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN
 * AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF THIS SOFTWARE.
 */

package tools.aqua.z3turnkey;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.z3.Native;
import org.junit.jupiter.api.Test;

/** Test that Z3 native methods can successfully be invoked. */
public class Z3LoaderTest {

  /** Invoke the {@link Native#getFullVersion} method from Z3. */
  @Test
  public void testLoading() {
    String version = Native.getFullVersion();
    String expectedVersion = System.getProperty("expectedZ3Version");
    assertTrue(
        version.contains(expectedVersion), "The loaded lib should report the expected version");
  }
}
