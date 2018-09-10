# bug-report-classifier

Open source software typically records bugs encountered during software development as bug 
reports and stores them in bug reports repositories. Bug reports contain descriptions of bugs
and status of resolution. Developers and users can submit bug reports or comment on bug
reports so that they can work together to resolve software bugs. However, due to the large
number of bug reports, manual triage of bug reports can be time-consuming. Therefore, it is
of great importance to study the automatic triage of software bugs to improve the efficiency
of software development. Most existing methods are based on machine learning algorithms,
which learn the historical bug report text to build classifiers and then assign the newly
generated bug report to a suitable developer. However, the assignee field is not necessarily a
developer, and more than one person may be interested in a bug report.

This thesis recommends a bug report to several developers who may be interested in it based
on the historical text and comment area of bug reports. First, bug reports are downloaded from
Internet using crawler technology. The bug reports are preprocessed to obtain the bug report
dataset, which is then used to build a multi-labels classifier. The classifier can learn the new
bug report’s text and generate an initial recommendation list. At the same time, the cooperation
coefficient matrix between developers is calculated by counting the number of specific metapaths in heterogeneous networks. After obtaining the cooperation coefficient matrix, each
developer's collaborator can be added to the initial recommendation list and get the final
recommendation list. The Eclipse bug report dataset is used to verify this method with 6
different multi-labels classification methods and 5 evaluation indicators. The experimental
results show that the software bug triage method based on heterogeneous network analysis is
more effective than that based on multi-label classification.
