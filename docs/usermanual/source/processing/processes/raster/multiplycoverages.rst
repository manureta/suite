.. _processing.processes.raster.multiplycoverages:

.. warning:: Document Status: **Requires questions answered (MP)**

MultiplyCoverages
=================

Description
-----------

The ``gs:MultiplyCoverages`` process takes two input grid coverages with a single band and performs a cell-by-cell multiplication on them, generating a new grid coverage. Each cell in the output grid has the value resulting from the multiplication of the corresponding values for that same cell in the input grids. Coverages are assumed to have a single band. If multiband layers are entered, only the values in the first band will be used.


.. figure:: img/multiplycoverages.png

   *gs:MultiplyCoverages*



Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.rasterin` and returns :ref:`processing.processes.formats.rasterout`.

Inputs
~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Usage
   * - ``coverageA``
     - First input coverage
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Required
   * - ``coverageB``
     - Second input coverage
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Required    

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - Output coverage
     - :ref:`GridCoverage2D <processing.processes.formats.rasterout>`


Usage notes
-----------

* Both input coverages must have the same bounding box and the same cell size. That means they must have the same width and height (in cells), so that their cell locations match exactly.
* The output coverage will have the same bounding box and cell size as the input.
* Both input grids must have only one band.
* This process can be used to "mask" certain cells in a given raster by using a mask grid with zero or ``NODATA`` values in those cells to be masked out. This is useful as a data-preparation technique when it is desired to restrict data to a certain area.
* Given two grid coverages with values in the (0,1) range representing probability (such as those produced by the ``gs:Heatmap`` process)  or suitability for a given activity, applying this process to them represents the equivalent of a fuzzy logic AND operation. This technique is commonly used in multi-criteria evaluation, multiplying grid coverages that contain a suitability value considering a single factor, to obtain the compound suitability of the activity considering all factors together. For instance, given an activity that is affected by terrain slope and temperature, and two grid coverages representing the corresponding suitability for each of those factors, the result of multiplying them is the suitability for that activity, considering both slope and temperature. As both coverages have values ranging from 0 to 1, the resulting values will also be within that range. A value of 0 (completely unsuitable) in a cell in one of the input grid coverages will result in a 0 value for that cell in the resulting coverage.

Examples
--------

Masking areas outside of an elevation range
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The following example masks all elevation areas outside of a certain elevation range, by multiplying the ``medford:elevation`` layer by a mask grid created using the :ref:`gs:RangeLookup <processing.processes.raster.rangelookup>` process and that same layer. This will allow for further calculation with values restricted to those cells within the elevation range.


Input parameters for ``gs:RangeLookup``

* ``data``: ``medford:elevation``
* ``band``: [Blank]
* ``ranges``: (1000;1500)
* ``noData``: [Blank]
* ``outputPixelValues``: 1

Input parameters for ``gs:MultiplyCoverages``

* ``coverageA``: ``medford:elevation``
* ``coverageA``: result of ``gs:RangeLookup``

:download:`Download complete chained XML request <xml/multiplycoverages.xml>`.

.. figure:: img/multiplycoveragesUI.png

   *gs:MultiplyCoverages example parameters (part 1)*


.. figure:: img/multiplycoveragesUI2.png

   *gs:MultiplyCoverages example parameters (part 2)*   

.. figure:: img/multiplycoveragesexample.png

   *gs:MultiplyCoverages example output*

Related processes
-----------------

* The :ref:`gs:AddCoverages <processing.processes.raster.addcoverages>` process performs a similar operation, adding cell values instead of multiplying them.
* The masking process mentioned above can be performed using a polygon feature collection instead of a grid coverage using the :ref:`gs:CropCoverage <processing.processes.raster.cropcoverage>` process.

