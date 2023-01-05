/*
 * MIT License
 *
 * Copyright (c) 2023 Overrun Organization
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.overrun.binpacking.test;

import org.overrun.binpacking.FixedPacker;
import org.overrun.binpacking.GrowingPacker;
import org.overrun.binpacking.Packer;
import org.overrun.binpacking.PackerRegion;

import java.util.List;

/**
 * packer test
 *
 * @author squid233
 * @since 0.1.0
 */
public final class PackerTest {
    private static List<PackerRegion<Object>> generateData() {
        return Packer.sort(
            PackerRegion.sized(100, 300),
            PackerRegion.sized(300, 300),
            PackerRegion.sized(200, 150),
            PackerRegion.sized(200, 200)
        );
    }

    private static void testFixed(List<PackerRegion<Object>> testData) {
        var packer = new FixedPacker(400, 500);
        packer.fit(testData);
        System.out.println(packer.width() + ", " + packer.height());
        testData.forEach(region ->
            region.ifFitPresent((r, f) ->
                System.out.println(f.x() + ", " + f.y() + ": " + r.width() + ", " + r.height())
            )
        );
    }

    private static void testGrowing(List<PackerRegion<Object>> testData) {
        var packer = new GrowingPacker();
        packer.fit(testData);
        System.out.println(packer.width() + ", " + packer.height());
        testData.forEach(region ->
            region.ifFitPresent((r, f) ->
                System.out.println(f.x() + ", " + f.y() + ": " + r.width() + ", " + r.height())
            )
        );
        var gp = new GrowingPacker();
        var s = Packer.sort(
            PackerRegion.delegate(PackerRegion.sized(2, 2), 6),
            PackerRegion.delegate(PackerRegion.sized(4, 2), 7)
        );
        gp.fit(s);
        s.forEach(region ->
            region.ifFitPresent((r, f) ->
                System.out.println(r.userdata())));
    }

    public static void main(String[] args) {
        var testData = generateData();
        System.out.println("----- FixedPacker -----");
        testFixed(testData);
        System.out.println("----- GrowingPacker -----");
        testGrowing(testData);
    }
}
