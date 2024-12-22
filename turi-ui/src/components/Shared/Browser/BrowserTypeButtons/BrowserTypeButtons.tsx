import { useTranslation } from 'react-i18next'
import { useState } from 'react'
import { GreyButton } from '../../Controls/Button'
import styles from './BrowserTypeButtons.module.css'

const STATES = {
    ALL: 'all',
    STAY: 'stay',
    ATTRACTION: 'attraction'
}

const BrowserTypeButtons = () => {
    const { t } = useTranslation()
    const [selectedButton, setSelectedButton] = useState<string>(STATES.ALL)

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={t('home.dashboard.all-button')}
                onClick={() => setSelectedButton(STATES.ALL)}
                className={selectedButton === STATES.ALL ? styles.selected : ''}
            />
            <GreyButton
                text={t('home.dashboard.stay-button')}
                onClick={() => setSelectedButton(STATES.STAY)}
                className={selectedButton === STATES.STAY ? styles.selected : ''}
            />
            <GreyButton
                text={t('home.dashboard.attraction-button')}
                onClick={() => setSelectedButton(STATES.ATTRACTION)}
                className={selectedButton === STATES.ATTRACTION ? styles.selected : ''}
            />
        </div>
    )
}

export default BrowserTypeButtons
