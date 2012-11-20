.. _processing.processes.vector.aggregate:

.. warning:: Document Status: Requires technical review

Aggregate
=========

Description
-----------

The ``gs:Aggregate`` process calculates one or more statistics for a given attribute for a feature collection. The available statistical values to calculate are mean, minimum value, maximum value, standard deviation, count and sum. 

.. figure :: img/aggregate.png
  
   *gs:Aggregate*

Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.fcin`

Inputs
^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Required
   * - ``features``
     - Input collection of point features to calculate statistics from
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcin>`
     - Yes
   * - ``aggregationAttribute``
     - The selected attribute to use for calculating statistics
     - String
     - Yes
   * - ``function``
     - the functions to calculate for the selected attribute.
     - Set<AggregationFunction>
     - Yes     
   * - ``singlePass``
     - If this parameter is True, calculations are done in a single pass
     - Boolean
     - No

Outputs
^^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - The results of the statistics computed for the selected attribute.
     - Results

Usage notes
------------

- The ``aggregationAttribute`` input parameters is case-sensitive.

Examples
---------

# Calculating the total number of students in the schools represented by the ``medford:schools`` feature collection.

  Input parameters:
    
  - ``features``: ``medford:schools``
  - ``aggregationAttribute``: ``students``
  - ``function``: ``Sum``
  - ``singlePass``: ``False``

  :download:`Download complete XML request <xml/aggregateexample.xml>`.

  .. figure:: img/aggregateexampleUI.png

     *gs:Aggregate example parameters*

  This yields a total number of students equal to 22342.


# Calculating the total number of students in the schools represented by the ``medford:schools`` feature collection, within the city limits represented by the ``medford:citylimits`` feature collection. The ``gs:InclusionFeatureLayer`` process is used to get a filtered collection of schools within the city limits, and then that resulting collection is used as input for the ``gs:Aggregate`` process.

  The parameter values used for the ``gs:InclusionFeatureLayer`` are the following ones:

  Input parameters for ``gs:InclusionFeatureLayer``

  * ``first feature collection``: *medford:schools*
  * ``second feature collection``: *medford:citylimits*

  Input parameters for ``gs:Aggregate``
    
  * ``features``: output from ``gs:InclusionFeatureLayer``
  * ``aggregationAttribute``: ``students``
  * ``function``: ``Sum``
  * ``singlePass``: ``False``

  :download:`Download complete XML request <xml/aggregateexample2.xml>`.

  .. figure:: img/aggregateexampleUI2.png

    *gs:Aggregate example parameters (Part 1)*

  .. figure:: img/aggregateexampleUI3.png

    *gs:Aggregate example parameters (Part 2)*    

  This yields a total number of students equal to 13432.    