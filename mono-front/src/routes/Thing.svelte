<script lang="ts">
    const emojis = {
        apple: '🍎',
        banana: '🍌',
        carrot: '🥕',
        doughnut: '🍩',
        egg: '🥚'
    };

    type Props = {
        name: keyof typeof emojis;
    };

    let { name }: Props = $props();

    const emoji = emojis[name];
</script>

<p>{emoji} = {name}</p>
