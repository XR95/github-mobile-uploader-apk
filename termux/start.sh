#!/data/data/com.termux/files/usr/bin/bash
cd "$HOME/glgonder" || exit 1
if tmux has-session -t githubuploader 2>/dev/null; then
  exit 0
fi
tmux new-session -d -s githubuploader 'cd "$HOME/glgonder" && python app.py'
