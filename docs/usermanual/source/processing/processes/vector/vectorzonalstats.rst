.. _processing.processes.vectorzonalstatistics:

.. warning:: Document Status: Requires technical review

VectorZonalStatistics
======================

Description
-----------

The ``gs:VectorZonalStatistics`` process calculates statistics of a points layer within a given set of polygons. It takes a points feature collection and a polygon one and creates a new polygon layer with the same geometries as this last one, but with additional attributes calculated from the points falling within each polygon. 

New attributes are computed using statistical parameters. The following parameters are computed: count, mean, minimum value, maximum value, standard deviation, sum. 

An attribute in the input points layer has to be selected. Calculation of statistics (except for the count value, which does not use it) is done based on the value of that attribute for each point layer.

.. image:: img/vectorzonalstats.png

Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.fcin` and returns :ref:`processing.processes.formats.fcout`.

Inputs
^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Required
   * - ``data``
     - Input collection of point features to calculate statistics from
     - SimpleFeatureCollection
     - Yes
   * - ``dataAttribute``
     - Name of attribute in the points layer to use for computing statistics. Name is case-sensitive. It should correspond to a numerical attribute.
     - String
     - Yes
   * - ``zones``
     - Feature collection with polygon geometries within which statistics are to be computed.
     - SimpleFeatureCollection
     - Yes

Outputs
^^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``statistics``
     - A polygon feature collection with the same geometries as the input polygon one, with additional attributes. Original attributes are prefixed by ‘z\_’. Additional attributes are named *count, min, max, avg, stddev* and *sum*.
     - SimpleFeatureCollection

Usage notes
------------

* Misuse of the aggregation analysis is likely to occur if the attribute from the feature collection layer is not carefully selected. While attributes representing some kind of counting (i.e. a population attribute) will yield sound results, attributes representing other statistical parameters (i.e, an "average income" attribute), might result in incorrect or meaningless aggregated values. Caution should be taken before interpreting the results of the aggregation process. It should also be noticed that the units used for aggregation (the shape and size of the input polygons, and the number of them) affect the result and cause what is known as the Modifiable Areal Unit Problem (MAUP). MAUP can be a significant cause of statistical bias. This problem is closely related to the so-called Ecological Fallacy.

Examples
---------

1) This example show how to calculate the number of urban areas in each world country, using the ``world:urbanareas1_1`` and ``world:borders`` feature collections.

- Process: ``gs:VectorZonalStatistics``

  - ``data``: *world:urbanareas1_1*
  - ``dataAttribute``: *pop2000*
  - ``zones``: *world:borders*

.. image:: img/vectorzonalstatsexampleUI.png  

See XML request :download:`here <xml/vectorzonalstatsexample.xml>`.

The following image shows the resulting feature collection, styled using the ``count`` attribute added, which represents the total number of urban areas within each country. The remaining statistical attributes refer to the ``pop2000`` attribute in the input point feature collection, so the ``sum`` attribute represent the total number of people living in urban areas in each country.

.. image:: img/vectorzonalstatsexample.png


2) The next example shows how to use the ``gs:Aggregate`` process for rendering purposes, aggregating points based on an arbitrary set of regular polygons covering the extension of the points layer can be used for this purpose. The ``gs:Grid`` process can be used to generate a regular grid of rectangles or hexagons. In this case, an hexagonal grid is used. 

The parameters for the ``gs:Grid`` process are the following ones.

- Process: ``gs:Grid``

  - ``bounds``
    
    - ``minX``:-180
    - ``maxX``: 180
    - ``minY``: -90
    - ``maxY``: 90
    - ``CRS``: *EPSG:4326*

  - ``width``: 2
  - ``mode``: *HexagonalFlat*

.. image:: img/vectorzonalstatsexampleUI2.png  

And the parameters for the ``gs:VectorZonalstatistics`` process are the following ones.

- Process: ``gsVectorZonalStatistics``

  - ``data``: *world:urbanareas1_1*
  - ``dataAttribute``: *pop2000*
  - ``zones``:  Output from ``gs:Grid`` process.

.. image:: img/vectorzonalstatsexampleUI3.png  

The XML of the whole process workflow can be found :download:`here <xml/vectorzonalstatsexample2.xml>`.

In the image below, the resulting feature collection from the aggregation process is styled with a gradient color ramp based on the ``count`` attribute added.

.. image:: img/vectorzonalstatsexample2.png

.. 2) The following example shows how to compute the total number of school students that can be expected to use each of the libraries in the ``medford:libraries`` feature collection. Computation is done in two steps. First, the influence area of each library (the area for which a given library is the closest one) is calculated using the ``gs:Voronoi`` process. Second, the resulting influence area feature collection is used as input to the ``gs:VectorZonalStatistics`` along with the ``medford:schools`` one. The ``students`` attribute is used to gather statistics. The new ``sum`` attribute reflects the total number of student from all the school that have a given library as their closest one.

.. image img/vectorzonalstastexample.png

.. The parameters for the ``gs:VectorZonalstatistics`` process are the following ones.

.. - ``data``: *medford:schools*
.. - ``dataAttribute``: *Students*
.. - ``zones``:  Output from ``gs:Voronoi`` process.

.. The XML of the whole process workflow can be found :download:`here <xml/vectorzonalstatsexample.xml>`.

Related processes
-------------------

The ``gs:RasterZonalStats`` process performs a similar analysis, but instead of a point feature collection, it takes a raster layer from which values are taken and analyzed.

Additional information
-------------------------

http://en.wikipedia.org/wiki/Ecological_fallacy