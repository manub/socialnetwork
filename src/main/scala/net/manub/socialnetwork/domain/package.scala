package net.manub.socialnetwork

package object domain {

  type Command = (SocialNetwork) => (SocialNetwork, List[Message])
  type CommandFactory = String => Command
}
