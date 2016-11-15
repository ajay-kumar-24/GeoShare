class TreeNode(object):
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

class Solution(object):
    def lca(self,root, p, q):
        if p < root.val and q < root.val:
            self.lca(root.left)
        elif p > root.val and q > root.val:
            self.lca(root.right)
        elif (root.val <= p and root.val >= q) or (root.val >= p and root.val <= q):
            return root.val
        else:
            return -1

    def lowestCommonAncestor(self, root, p, q):
        """
        :type root: TreeNode
        :type p: TreeNode
        :type q: TreeNode
        :rtype: TreeNode
        """
        if root == None:
            return []
        else:
            print self.lca(root)

