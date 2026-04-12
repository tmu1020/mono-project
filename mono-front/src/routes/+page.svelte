<h1>Svelte Tutorial (Introduction～Bindings)</h1>

<p>
    <input bind:value={name} /><br>
    Hello {name.toUpperCase()}!<br>
    {@html safe}
</p>

<img {src} alt="{name} dances."/>

<Nested />

<button onclick={increment}>
    Clicked {count} {count === 1 ? 'time' : 'times'}
</button>

{#if count > 10}
    <p>{count} is greater than 10</p>
{:else if count < 5}
    <p>{count} is less than 5</p>
{:else}
    <p>{count} is between 5 and 10</p>
{/if}

<p>{numbers.join(' + ')} = {total}</p>

<button onclick={addNumber}>
    Add a number
</button>

<br><br>

<button onclick={() => interval /= 2} class="normal">speed up</button>
<button onclick={() => interval *= 2}>slow down</button>

<p>elapsed: {elapsed}</p>

<Counter />
<Counter />
<Counter />
<br><br>

<Nested2 answer='42' />
<Nested2 />

<PackageInfo 
    name={pkg.name}
    version={pkg.version}
    description={pkg.description}
    website={pkg.website}
/>

<h1 style="color: {selected}">Pick a color</h1>

<div class="colors">
    {#each colors as color, i(color)}
        <button
            style="background: {color}"
            aria-label={color}
            aria-current={selected === color}
            onclick={() => selected = color}
            class="button-colors"
        >{i + 1}</button>
    {/each}
</div>

<button onclick={() => things.shift()}>
    Remove first thing
</button>

{#each things as thing (thing.id)}
    <Thing name={thing.name} />
{/each}

<button onclick={() => promise = roll()}>
    roll the dice
</button>

{#await promise}
    <p>...rolling</p>
{:then number} 
    <p>you rolled a {number}!</p>
{:catch error}
    <p style="color: red">{error.message}</p>
{/await}

<div class="pointer-box" onpointermove={(event) => {
    m.x = event.clientX;
    m.y = event.clientY;
}}>
    The pointer is at {Math.round(m.x)} x {Math.round(m.y)}
</div>

<div onkeydowncapture={(e) => alert(`<div> ${e.key}`)} role="presentation">
    <input onkeydowncapture={(e) => alert(`<input> ${e.key}`)} />
</div>

<p>The current value is {value}</p>
<Stepper
    increment={() => value += 1}
    decrement={() => value -= 1}
/><br><br>

<BigRedButton onclick={playHorn} /><br><br>

<label>
    <input type="number" bind:value={a} min="0" max="10" />
    <input type="range" bind:value={a} min="0" max="10" />
</label>

<label>
    <input type="number" bind:value={b} min="0" max="10" />
    <input type="range" bind:value={b} min="0" max="10" />
</label>

<p>{a} + {b} = {a + b}</p>

<label>
    <input type="checkbox" bind:checked={yes} />
    Yes! Send me regular email spam
</label>

{#if yes}
    <p>
        Thank you. We will bombard your inbox and sell your personal details.
    </p>
{:else}
    <p>
        You must opt in to continue. If you're not paying, you're the product.
    </p>
{/if}

<button disabled={!yes}>Subscribe</button>

<h2>Insecurity questions</h2>

<form onsubmit={handleSubmit}>
    <select
        bind:value={select}
        onchange={() => (answer = '')}
    >
        {#each questions as question(question.id)}
            <option value={question}>
                {question.text}
            </option>
        {/each}
    </select><br>
    <input bind:value={answer} />
    <button disabled={!answer} type="submit">
        Submit
    </button>
</form>

<p>
    selected question {select ? select.id: '[waiting...]'}
</p>

<h2>Size</h2>

{#each [1,2,3] as number(number)}
    <label>
        <input
            type="radio"
            name="scoops"
            value={number}
            bind:group={scoops}
        />
        {number} {number === 1 ? 'scoop' : 'scoops'}
    </label>
{/each}

<h2>Flavours</h2>

{#each ['cookies and cream', 'mint choc chip', 'rasberry ripple'] as flavour(flavour)}
    <label>
        <input 
            type="checkbox"
            name="flavours"
            value={flavour}
            bind:group={flavours}
        />
        {flavour}
    </label>
{/each} <br><br>

<select multiple bind:value={flavours}>
    {#each ['cookies and cream', 'mint choc chip', 'rasberry ripple'] as flavour(flavour)}
        <option>{flavour}</option>
    {/each}
</select>

{#if flavours.length === 0}
    <p>Please select at least one flavour</p>
{:else if flavours.length > scoops}
    <p>Can't order more flavours than scoops!</p>
{:else}
    <p>
        You ordered {scoops} {scoops === 1 ? 'scoop' : 'scoops'}
        of {formatter.format(flavours)}
    </p>
{/if}

<div class="grid">
	input
	<textarea bind:value={textValue}></textarea>

	output
	<div>{@html marked(textValue)}</div>
</div>

<script lang="ts">
    import Nested from './Nested.svelte';
	import Counter from './Counter.svelte';
	import Nested2 from './Nested2.svelte';
    import PackageInfo from './packageInfo.svelte';
    import Thing from './Thing.svelte';
    import { roll } from './utils.js';
    import Stepper from './Stepper.svelte';
    import BigRedButton from './BigRedButton.svelte';
    import { onMount } from 'svelte';
	import { marked } from 'marked';
    import DOMPurify from "dompurify";

    let safe = $state(`this string contains some <strong>HTML!!!</strong>`);
    onMount(() => {
        safe = DOMPurify.sanitize(safe);
    });
    let name = $state('world');
    let src = '/images/man-dances.gif';
    let count = $state(0);
    let numbers = $state([1,2,3,4]);
    let total = $derived(numbers.reduce((t, n) => t + n, 0));
    let elapsed = $state(0);
    let interval = $state(1000);
    let promise = $state(roll());
    let m = $state({ x: 0, y: 0 });
    let value = $state(0);
    let horn: HTMLAudioElement;
    let a = $state(1);
    let b = $state(2);
    let yes = $state(false);
    type Question = {
	id: number;
	text: string;
    };
	let questions = [
		{
			id: 1,
			text: `Where did you go to school?`
		},
		{
			id: 2,
			text: `What is your mother's name?`
		},
		{
			id: 3,
			text: `What is another personal fact that an attacker could easily find with Google?`
		}
	];
	let select = $state<Question | null>(null);
	let answer = $state('');
    let scoops = $state(1);
    let flavours = $state([]);
    const formatter = new Intl.ListFormat('en', { style: 'long', type: 'conjunction'});
    let textValue = $state(`Some words are *italic*, some are **bold**\n\n- lists\n- are\n- cool`);
    onMount(() => {
        textValue = DOMPurify.sanitize(textValue);
    });
    
    const pkg = {
        name: 'svelte',
        version: 5,
        description: 'blazing fast',
        website: 'https://svelte.dev'
    };
    const colors = ['red', 'orange', 'yellow', 'green', 'blue', 'indigo', 'violet'];
    let selected = $state(colors[0]);

    type EmojiName = "apple" | "banana" | "carrot" | "doughnut" | "egg";

    let things = $state<{ id: number; name: EmojiName }[]>([
        { id: 1, name: 'apple' },
        { id: 2, name: 'banana' },
        { id: 3, name: 'carrot' },
        { id: 4, name: 'doughnut' },
        { id: 5, name: 'egg' }
    ]);

    function increment() {
        count += 1;
    }

    function addNumber() {
        numbers.push(numbers.length + 1);
        console.log($state.snapshot(numbers));
    }

    $effect(() => {
        const id = setInterval(() => {
            elapsed += 1;
        }, interval);

        return () => {
            clearInterval(id);
        };
    });

    // function onpointermove(event: PointerEvent) {
    //     m.x = event.clientX;
    //     m.y = event.clientY;
    // }

    onMount(() => {
        horn = new Audio('/horn.mp3');
    });

    function playHorn() {
        horn.load();
        horn.play();
    }

    function handleSubmit(e: Event) {
        e.preventDefault();

        if (!select) return;

        alert(
            `answered question ${select.id} (${select.text}) with "${answer}"`
        );
    }
</script>
<style>
    p {
        color: goldenrod;
        font-family: 'Comic Sans MS', cursive;
        font-size: 2em;
    }

    h1 {
        font-size: 2rem;
        font-weight: 700;
        transition: color 0.2s;
    }

    .colors {
        display: grid;
        grid-template-columns: repeat(7, 1fr);
        grid-gap: 5px;
        max-width: 400px;
    }

    .button-colors {
        aspect-ratio: 1;
        border-radius: 50%;
        background: var(--color, #fff);
        transform: translate(-2px,-2px);
        filter: drop-shadow(2px 2px 3px rgba(0,0,0,0.2));
        transition: all;
        color: black;
        font-weight: 700;
        font-size: 2rem;
    }

    .button-colors[aria-current="true"] {
        transform: none;
        filter: none;
        box-shadow: inset 3px 3px 4px rgba(0,0,0,0.2);
    }

    .pointer-box {
        height: 200px;
        border: 1px solid #000;
        padding: 1rem;
    } 

	.grid {
		display: grid;
		grid-template-columns: 5em 1fr;
		grid-template-rows: 1fr 1fr;
		grid-gap: 1em;
		height: 100%;
	}

	textarea {
		flex: 1;
		resize: none;
	}
</style>

