.. _processing.processes.raster.addoverages:

.. warning:: Document Status: **Requires additional technical review and example (MP)**

AddCoverages
=================

Description
-----------

The ``gs:AddCoverages`` process takes two input grid coverages and perform a cell by cell addition on them, generating a new grid coverage. Each cell in the output grid has the value resulting from the addition of the corresponding values for that same cell in the input grids.

.. figure:: img/addcoverages.png

   *gs:AddCoverages*



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
     - The output grid coverage
     - :ref:`GridCoverage2D<processing.processes.formats.rasterout>`


Usage notes
-----------

* Both input grid coverages must have the same bounding box and the same cellsize. That means they must have the same width and height (in cells), so they match and they can be multiplied.
* The output grid coverage will have the same bounding box and cellsize as the input grids.
* Given two probability raster with values in the (0,1) range (such as those produced by the ``gs:Heatmap`` process), applying this process to them represents the equivalent of a fuzzy logic OR operation.


Examples
---------

..todo:


Related processes
-----------------

* The ``gs:MultiplyCoverages``<processing.processes.raster.multiplycoverages>`` process performs a similar operation, mutiplying cell values from two input grids instead of adding them.


