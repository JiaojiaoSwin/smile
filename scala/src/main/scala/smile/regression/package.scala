/*******************************************************************************
 * (C) Copyright 2015 Haifeng Li
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package smile

import smile.regression._
import smile.data._
import smile.math._, kernel._
import smile.util._

/**
 * Regression shell commands.
 *
 * @author Haifeng Li
 */
package object regression {

  /**
   * Apply a regression model on a data sample.
   *
   * @param model regression model
   * @param x data sample
   * @tparam T the data type
   * @return predicted value of dependent variable.
   */
  def predict[T <: AnyRef](model: Regression[T], x: T): Double = {
    model.predict(x)
  }

  /**
   * Ordinary least squares. In linear regression,
   * the model specification is that the dependent variable is a linear
   * combination of the parameters (but need not be linear in the independent
   * variables). The residual is the difference between the value of the
   * dependent variable predicted by the model, and the true value of the
   * dependent variable. Ordinary least squares obtains parameter estimates
   * that minimize the sum of squared residuals, SSE (also denoted RSS).
   *
   * The OLS estimator is consistent when the independent variables are
   * exogenous and there is no multicollinearity, and optimal in the class
   * of linear unbiased estimators when the errors are homoscedastic and
   * serially uncorrelated. Under these conditions, the method of OLS provides
   * minimum-variance mean-unbiased estimation when the errors have finite
   * variances.
   *
   * There are several different frameworks in which the linear regression
   * model can be cast in order to make the OLS technique applicable. Each
   * of these settings produces the same formulas and same results, the only
   * difference is the interpretation and the assumptions which have to be
   * imposed in order for the method to give meaningful results. The choice
   * of the applicable framework depends mostly on the nature of data at hand,
   * and on the inference task which has to be performed.
   *
   * Least squares corresponds to the maximum likelihood criterion if the
   * experimental errors have a normal distribution and can also be derived
   * as a method of moments estimator.
   *
   * Once a regression model has been constructed, it may be important to
   * confirm the goodness of fit of the model and the statistical significance
   * of the estimated parameters. Commonly used checks of goodness of fit
   * include the R-squared, analysis of the pattern of residuals and hypothesis
   * testing. Statistical significance can be checked by an F-test of the overall
   * fit, followed by t-tests of individual parameters.
   *
   * Interpretations of these diagnostic tests rest heavily on the model
   * assumptions. Although examination of the residuals can be used to
   * invalidate a model, the results of a t-test or F-test are sometimes more
   * difficult to interpret if the model's assumptions are violated.
   * For example, if the error term does not have a normal distribution,
   * in small samples the estimated parameters will not follow normal
   * distributions and complicate inference. With relatively large samples,
   * however, a central limit theorem can be invoked such that hypothesis
   * testing may proceed using asymptotic approximations.
   *
   * @param x a matrix containing the explanatory variables.
   * @param y the response values.
   */
  def ols(x: Array[Array[Double]], y: Array[Double]): OLS = {
    time {
      new OLS(x, y)
    }
  }

  /**
   * Ridge Regression. When the predictor variables are highly correlated amongst
   * themselves, the coefficients of the resulting least squares fit may be very
   * imprecise. By allowing a small amount of bias in the estimates, more
   * reasonable coefficients may often be obtained. Ridge regression is one
   * method to address these issues. Often, small amounts of bias lead to
   * dramatic reductions in the variance of the estimated model coefficients.
   * Ridge regression is such a technique which shrinks the regression
   * coefficients by imposing a penalty on their size. Ridge regression was
   * originally developed to overcome the singularity of the X'X matrix.
   * This matrix is perturbed so as to make its determinant appreciably
   * different from 0.
   *
   * Ridge regression is a kind of Tikhonov regularization, which is the most
   * commonly used method of regularization of ill-posed problems. Another
   * interpretation of ridge regression is available through Bayesian estimation.
   * In this setting the belief that weight should be small is coded into a prior
   * distribution.
   *
   * @param x a matrix containing the explanatory variables.
   * @param y the response values.
   * @param lambda the shrinkage/regularization parameter.
   */
  def ridge(x: Array[Array[Double]], y: Array[Double], lambda: Double): RidgeRegression = {
    time {
      new RidgeRegression(x, y, lambda)
    }
  }

  /**
   * Least absolute shrinkage and selection operator.
   * The Lasso is a shrinkage and selection method for linear regression.
   * It minimizes the usual sum of squared errors, with a bound on the sum
   * of the absolute values of the coefficients (i.e. L<sub>1</sub>-regularized).
   * It has connections to soft-thresholding of wavelet coefficients, forward
   * stage-wise regression, and boosting methods.
   * <p>
   * The Lasso typically yields a sparse solution, of which the parameter
   * vector &beta; has relatively few nonzero coefficients. In contrast, the
   * solution of L<sub>2</sub>-regularized least squares (i.e. ridge regression)
   * typically has all coefficients nonzero. Because it effectively
   * reduces the number of variables, the Lasso is useful in some contexts.
   * <p>
   * For over-determined systems (more instances than variables, commonly in
   * machine learning), we normalize variables with mean 0 and standard deviation
   * 1. For under-determined systems (less instances than variables, e.g.
   * compressed sensing), we assume white noise (i.e. no intercept in the linear
   * model) and do not perform normalization. Note that the solution
   * is not unique in this case.
   * <p>
   * There is no analytic formula or expression for the optimal solution to the
   * L<sub>1</sub>-regularized least squares problems. Therefore, its solution
   * must be computed numerically. The objective function in the
   * L<sub>1</sub>-regularized least squares is convex but not differentiable,
   * so solving it is more of a computational challenge than solving the
   * L<sub>2</sub>-regularized least squares. The Lasso may be solved using
   * quadratic programming or more general convex optimization methods, as well
   * as by specific algorithms such as the least angle regression algorithm.
   *
   * <h2>References</h2>
   * <ol>
   * <li> R. Tibshirani. Regression shrinkage and selection via the lasso. J. Royal. Statist. Soc B., 58(1):267-288, 1996.</li>
   * <li> B. Efron, I. Johnstone, T. Hastie, and R. Tibshirani. Least angle regression. Annals of Statistics, 2003 </li>
   * <li> Seung-Jean Kim, K. Koh, M. Lustig, Stephen Boyd, and Dimitry Gorinevsky. An Interior-Point Method for Large-Scale L1-Regularized Least Squares. IEEE JOURNAL OF SELECTED TOPICS IN SIGNAL PROCESSING, VOL. 1, NO. 4, 2007.</li>
   * </ol>
   *
   * @param x a matrix containing the explanatory variables.
   * @param y the response values.
   * @param lambda the shrinkage/regularization parameter.
   * @param tol the tolerance for stopping iterations (relative target duality gap).
   * @param maxIter the maximum number of iterations.
   */
  def lasso(x: Array[Array[Double]], y: Array[Double], lambda: Double, tol: Double = 1E-3, maxIter: Int = 5000): LASSO = {
    time {
      new LASSO(x, y, lambda, tol, maxIter)
    }
  }

  /**
   * Support vector regression. Like SVMs for classification, the model produced
   * by SVR depends only on a subset of the training data, because the cost
   * function ignores any training data close to the model prediction (within
   * a threshold).
   *
   * @param x training data
   * @param y training labels
   * @param kernel the kernel function.
   * @param eps the loss function error threshold.
   * @param C the soft margin penalty parameter.
   * @param tol the tolerance of convergence test.
   * @tparam T the data type
   *
   * @return SVR model.
   */
  def svr[T <: AnyRef](x: Array[T], y: Array[Double], kernel: MercerKernel[T], eps: Double, C: Double, tol: Double = 1E-3): SVR[T] = {
    new SVR[T](x, y, kernel, eps, C, tol)
  }

  /**
   * Support vector regression. Like SVMs for classification, the model produced
   * by SVR depends only on a subset of the training data, because the cost
   * function ignores any training data close to the model prediction (within
   * a threshold).
   *
   * @param x training data
   * @param y training labels
   * @param weight positive instance weight. The soft margin penalty
   *               parameter for instance i will be weight[i] * C.
   * @param kernel the kernel function.
   * @param eps the loss function error threshold.
   * @param C the soft margin penalty parameter.
   * @param tol the tolerance of convergence test.
   * @tparam T the data type
   *
   * @return SVR model.
   */
  def svr[T <: AnyRef](x: Array[T], y: Array[Double], weight: Array[Double], kernel: MercerKernel[T], eps: Double, C: Double, tol: Double): SVR[T] = {
    new SVR[T](x, y, weight, kernel, eps, C, tol)
  }

  /**
   * Decision tree for classification. A decision tree can be learned by
   * splitting the training set into subsets based on an attribute value
   * test. This process is repeated on each derived subset in a recursive
   * manner called recursive partitioning. The recursion is completed when
   * the subset at a node all has the same value of the target variable,
   * or when splitting no longer adds value to the predictions.
   * <p>
   * The algorithms that are used for constructing decision trees usually
   * work top-down by choosing a variable at each step that is the next best
   * variable to use in splitting the set of items. "Best" is defined by how
   * well the variable splits the set into homogeneous subsets that have
   * the same value of the target variable. Different algorithms use different
   * formulae for measuring "best". Used by the CART algorithm, Gini impurity
   * is a measure of how often a randomly chosen element from the set would
   * be incorrectly labeled if it were randomly labeled according to the
   * distribution of labels in the subset. Gini impurity can be computed by
   * summing the probability of each item being chosen times the probability
   * of a mistake in categorizing that item. It reaches its minimum (zero) when
   * all cases in the node fall into a single target category. Information gain
   * is another popular measure, used by the ID3, C4.5 and C5.0 algorithms.
   * Information gain is based on the concept of entropy used in information
   * theory. For categorical variables with different number of levels, however,
   * information gain are biased in favor of those attributes with more levels.
   * Instead, one may employ the information gain ratio, which solves the drawback
   * of information gain.
   * <p>
   * Classification and Regression Tree techniques have a number of advantages
   * over many of those alternative techniques.
   * <dl>
   * <dt>Simple to understand and interpret.</dt>
   * <dd>In most cases, the interpretation of results summarized in a tree is
   * very simple. This simplicity is useful not only for purposes of rapid
   * classification of new observations, but can also often yield a much simpler
   * "model" for explaining why observations are classified or predicted in a
   * particular manner.</dd>
   * <dt>Able to handle both numerical and categorical data.</dt>
   * <dd>Other techniques are usually specialized in analyzing datasets that
   * have only one type of variable. </dd>
   * <dt>Tree methods are nonparametric and nonlinear.</dt>
   * <dd>The final results of using tree methods for classification or regression
   * can be summarized in a series of (usually few) logical if-then conditions
   * (tree nodes). Therefore, there is no implicit assumption that the underlying
   * relationships between the predictor variables and the dependent variable
   * are linear, follow some specific non-linear link function, or that they
   * are even monotonic in nature. Thus, tree methods are particularly well
   * suited for data mining tasks, where there is often little a priori
   * knowledge nor any coherent set of theories or predictions regarding which
   * variables are related and how. In those types of data analytics, tree
   * methods can often reveal simple relationships between just a few variables
   * that could have easily gone unnoticed using other analytic techniques.</dd>
   * </dl>
   * One major problem with classification and regression trees is their high
   * variance. Often a small change in the data can result in a very different
   * series of splits, making interpretation somewhat precarious. Besides,
   * decision-tree learners can create over-complex trees that cause over-fitting.
   * Mechanisms such as pruning are necessary to avoid this problem.
   * Another limitation of trees is the lack of smoothness of the prediction
   * surface.
   * <p>
   * Some techniques such as bagging, boosting, and random forest use more than
   * one decision tree for their analysis.
   *
   * @param x the training instances.
   * @param y the response variable.
   * @param J the maximum number of leaf nodes in the tree.
   * @param attributes the attribute properties.
   * @return Regression tree model.
   */
  def cart(x: Array[Array[Double]], y: Array[Double], J: Int, attributes: Array[Attribute] = null): RegressionTree = {
    val p = x(0).length

    val attr = if (attributes == null) {
      val attr = new Array[Attribute](p)
      for (i <- 0 until p) attr(i) = new NumericAttribute(s"V$i")
      attr
    } else attributes

    time {
      new RegressionTree(attr, x, y, J)
    }
  }

  /**
   * Random forest for classification. Random forest is an ensemble classifier
   * that consists of many decision trees and outputs the majority vote of
   * individual trees. The method combines bagging idea and the random
   * selection of features.
   * <p>
   * Each tree is constructed using the following algorithm:
   * <ol>
   * <li> If the number of cases in the training set is N, randomly sample N cases
   * with replacement from the original data. This sample will
   * be the training set for growing the tree.
   * <li> If there are M input variables, a number m &lt;&lt; M is specified such
   * that at each node, m variables are selected at random out of the M and
   * the best split on these m is used to split the node. The value of m is
   * held constant during the forest growing.
   * <li> Each tree is grown to the largest extent possible. There is no pruning.
   * </ol>
   * The advantages of random forest are:
   * <ul>
   * <li> For many data sets, it produces a highly accurate classifier.
   * <li> It runs efficiently on large data sets.
   * <li> It can handle thousands of input variables without variable deletion.
   * <li> It gives estimates of what variables are important in the classification.
   * <li> It generates an internal unbiased estimate of the generalization error
   * as the forest building progresses.
   * <li> It has an effective method for estimating missing data and maintains
   * accuracy when a large proportion of the data are missing.
   * </ul>
   * The disadvantages are
   * <ul>
   * <li> Random forests are prone to over-fitting for some datasets. This is
   * even more pronounced on noisy data.
   * <li> For data including categorical variables with different number of
   * levels, random forests are biased in favor of those attributes with more
   * levels. Therefore, the variable importance scores from random forest are
   * not reliable for this type of data.
   * </ul>
   *
   * @param x the training instances.
   * @param y the response variable.
   * @param attributes the attribute properties. If not provided, all attributes
   *                   are treated as numeric values.
   * @param T the number of trees.
   * @param mtry the number of input variables to be used to determine the decision
   *             at a node of the tree. dim/3 seems to give generally good performance,
   *             where dim is the number of variables.
   * @param S the number of instances in a node below which the tree will
   *          not split, setting S = 5 generally gives good results.
   *
   * @return Random forest classification model.
   */
  def randomForest(x: Array[Array[Double]], y: Array[Double], attributes: Array[Attribute] = null, T: Int = 500, mtry: Int = -1, S: Int = 5): RandomForest = {
    val p = x(0).length

    val attr = if (attributes == null) {
      val attr = new Array[Attribute](p)
      for (i <- 0 until p) attr(i) = new NumericAttribute(s"V$i")
      attr
    } else attributes

    val m = if (mtry <= 0) p / 3 else mtry

    time {
      new RandomForest(attr, x, y, T, m, S)
    }
  }

  /**
   * Gradient boosting for classification. Gradient boosting is typically used
   * with decision trees (especially CART regression trees) of a fixed size as
   * base learners. For this special case Friedman proposes a modification to
   * gradient boosting method which improves the quality of fit of each base
   * learner.
   * <p>
   * Generic gradient boosting at the t-th step would fit a regression tree to
   * pseudo-residuals. Let J be the number of its leaves. The tree partitions
   * the input space into J disjoint regions and predicts a constant value in
   * each region. The parameter J controls the maximum allowed
   * level of interaction between variables in the model. With J = 2 (decision
   * stumps), no interaction between variables is allowed. With J = 3 the model
   * may include effects of the interaction between up to two variables, and
   * so on. Hastie et al. comment that typically 4 &le; J &le; 8 work well
   * for boosting and results are fairly insensitive to the choice of in
   * this range, J = 2 is insufficient for many applications, and J &gt; 10 is
   * unlikely to be required.
   * <p>
   * Fitting the training set too closely can lead to degradation of the model's
   * generalization ability. Several so-called regularization techniques reduce
   * this over-fitting effect by constraining the fitting procedure.
   * One natural regularization parameter is the number of gradient boosting
   * iterations T (i.e. the number of trees in the model when the base learner
   * is a decision tree). Increasing T reduces the error on training set,
   * but setting it too high may lead to over-fitting. An optimal value of T
   * is often selected by monitoring prediction error on a separate validation
   * data set.
   * <p>
   * Another regularization approach is the shrinkage which times a parameter
   * &eta; (called the "learning rate") to update term.
   * Empirically it has been found that using small learning rates (such as
   * &eta; &lt; 0.1) yields dramatic improvements in model's generalization ability
   * over gradient boosting without shrinking (&eta; = 1). However, it comes at
   * the price of increasing computational time both during training and
   * prediction: lower learning rate requires more iterations.
   * <p>
   * Soon after the introduction of gradient boosting Friedman proposed a
   * minor modification to the algorithm, motivated by Breiman's bagging method.
   * Specifically, he proposed that at each iteration of the algorithm, a base
   * learner should be fit on a subsample of the training set drawn at random
   * without replacement. Friedman observed a substantial improvement in
   * gradient boosting's accuracy with this modification.
   * <p>
   * Subsample size is some constant fraction f of the size of the training set.
   * When f = 1, the algorithm is deterministic and identical to the one
   * described above. Smaller values of f introduce randomness into the
   * algorithm and help prevent over-fitting, acting as a kind of regularization.
   * The algorithm also becomes faster, because regression trees have to be fit
   * to smaller datasets at each iteration. Typically, f is set to 0.5, meaning
   * that one half of the training set is used to build each base learner.
   * <p>
   * Also, like in bagging, sub-sampling allows one to define an out-of-bag
   * estimate of the prediction performance improvement by evaluating predictions
   * on those observations which were not used in the building of the next
   * base learner. Out-of-bag estimates help avoid the need for an independent
   * validation dataset, but often underestimate actual performance improvement
   * and the optimal number of iterations.
   * <p>
   * Gradient tree boosting implementations often also use regularization by
   * limiting the minimum number of observations in trees' terminal nodes.
   * It's used in the tree building process by ignoring any splits that lead
   * to nodes containing fewer than this number of training set instances.
   * Imposing this limit helps to reduce variance in predictions at leaves.
   *
   * <ol>
   * <li> J. H. Friedman. Greedy Function Approximation: A Gradient Boosting Machine, 1999.</li>
   * <li> J. H. Friedman. Stochastic Gradient Boosting, 1999.</li>
   * </ol>
   *
   * @param x the training instances.
   * @param y the class labels.
   * @param attributes the attribute properties. If not provided, all attributes
   *                   are treated as numeric values.
   * @param loss loss function for regression. By default, least absolute
   *             deviation is employed for robust regression.
   * @param T the number of iterations (trees).
   * @param J the number of leaves in each tree.
   * @param eta the shrinkage parameter in (0, 1] controls the learning rate of procedure.
   * @param f the sampling fraction for stochastic tree boosting.
   *
   * @return Gradient boosted trees.
   */
  def gbm(x: Array[Array[Double]], y: Array[Double], attributes: Array[Attribute] = null, loss: GradientTreeBoost.Loss = GradientTreeBoost.Loss.LeastAbsoluteDeviation, T: Int = 500, J: Int = 6, eta: Double = 0.05, f: Double = 0.7): GradientTreeBoost = {
    val p = x(0).length

    val attr = if (attributes == null) {
      val attr = new Array[Attribute](p)
      for (i <- 0 until p) attr(i) = new NumericAttribute(s"V$i")
      attr
    } else attributes

    time {
      new GradientTreeBoost(attr, x, y, loss, T, J, eta, f)
    }
  }
}