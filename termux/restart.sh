#!/data/data/com.termux/files/usr/bin/bash
tmux kill-session -t githubuploader 2>/dev/null || true
sleep 1
exec "$HOME/glgonder/start.sh"
